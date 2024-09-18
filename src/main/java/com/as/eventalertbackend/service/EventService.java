package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.dto.request.EventFilterRequestDto;
import com.as.eventalertbackend.dto.request.EventRequestDto;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.Event;
import com.as.eventalertbackend.jpa.entity.EventSeverity;
import com.as.eventalertbackend.jpa.entity.EventTag;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.reopsitory.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class EventService {

    private final EventRepository eventRepository;

    private final EventSeverityService severityService;
    private final EventTagService tagService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public EventService(EventRepository eventRepository,
                        EventSeverityService severityService,
                        EventTagService tagService,
                        UserService userService,
                        NotificationService notificationService) {
        this.eventRepository = eventRepository;
        this.severityService = severityService;
        this.tagService = tagService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND));
    }

    public List<Event> findAllByUserId(Long userId) {
        return eventRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Transactional
    public Page<Event> findByFilter(EventFilterRequestDto filterRequestDto, int pageSize, int pageNumber, Order order) {
        if (pageSize > AppConstants.MAX_PAGES) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_PAGE_SIZE);
        }

        if (filterRequestDto.getStartDate().isAfter(filterRequestDto.getEndDate())) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_END_DATE_AFTER_START_DATE);
        }

        if (filterRequestDto.getEndDate().getYear() - filterRequestDto.getStartDate().getYear() > AppConstants.MAX_YEARS_INTERVAL) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_YEARS_INTERVAL);
        }

        if (order == null) {
            order = Order.BY_DATE_DESCENDING;
        }

        LocalDateTime startDateTime = filterRequestDto.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = filterRequestDto.getEndDate().atTime(23, 59, 59);

        log.info("Starting events retrieval; latitude: {}, longitude: {}, radius: {}, start date: {}, end date: {}, tags: {}, severities: {}",
                filterRequestDto.getLatitude(), filterRequestDto.getLongitude(), filterRequestDto.getRadius(),
                startDateTime, endDateTime,
                filterRequestDto.getTagsIds(), filterRequestDto.getSeveritiesIds());

        List<EventRepository.DistanceProjection> distanceProjections = eventRepository.findByFilter(
                filterRequestDto.getLatitude(), filterRequestDto.getLongitude(), filterRequestDto.getRadius(),
                startDateTime, endDateTime,
                filterRequestDto.getTagsIds(), filterRequestDto.getSeveritiesIds());

        if (order == Order.BY_DISTANCE_DESCENDING) {
            Collections.reverse(distanceProjections);
        }

        long[] eventsIds = distanceProjections.stream()
                .mapToLong(EventRepository.DistanceProjection::getId)
                .toArray();

        PageRequest pageRequest;
        switch (order) {
            default:
                pageRequest = PageRequest.of(pageNumber, pageSize);
                break;
            case BY_DATE_ASCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("date_time"));
                break;
            case BY_DATE_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("date_time").descending());
                break;
            case BY_SEVERITY_ASCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("severity_id"));
                break;
            case BY_SEVERITY_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("severity_id").descending());
                break;
            case BY_DISTANCE_ASCENDING, BY_DISTANCE_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, JpaSort.unsafe("field(id, ?1)"));
                break;
        }

        Page<Event> eventsPage = eventRepository.findByIds(eventsIds, pageRequest);

        eventsPage.get().forEach(event ->
                distanceProjections.stream()
                        .filter(distanceProjection -> distanceProjection.getId().longValue() == event.getId().longValue())
                        .findFirst()
                        .ifPresent(distanceProjection -> event.setDistance(distanceProjection.getDistance()))
        );

        log.info("Retrieved total pages: {}, total elements: {}, events: {}",
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getContent().stream().mapToLong(Event::getId).toArray());

        return eventsPage;
    }

    public Event save(EventRequestDto eventRequestDto) {
        Event newEvent = createOrUpdate(new Event(), eventRequestDto);
        notificationService.send(newEvent);
        return newEvent;
    }

    public Event updateById(EventRequestDto eventRequestDto, Long id) {
        Event event = findById(id);
        return createOrUpdate(event, eventRequestDto);
    }

    public void deleteById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND);
        }
    }

    private Event createOrUpdate(Event event, EventRequestDto eventRequestDto) {
        User user = userService.findById(eventRequestDto.getUserId());
        EventTag tag = tagService.findById(eventRequestDto.getTagId());
        EventSeverity severity = severityService.findById(eventRequestDto.getSeverityId());

        if (isNullOrEmpty(user.getFirstName())) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_FIRST_NAME_MANDATORY);
        }
        if (isNullOrEmpty(user.getLastName())) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_LAST_NAME_MANDATORY);
        }

        String description = eventRequestDto.getDescription() == null ?
                null : eventRequestDto.getDescription().replaceAll("\n", " ");

        event.setLatitude(eventRequestDto.getLatitude());
        event.setLongitude(eventRequestDto.getLongitude());
        event.setImagePath(eventRequestDto.getImagePath());
        event.setDescription(description);
        event.setSeverity(severity);
        event.setTag(tag);
        event.setUser(user);

        return eventRepository.save(event);
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
