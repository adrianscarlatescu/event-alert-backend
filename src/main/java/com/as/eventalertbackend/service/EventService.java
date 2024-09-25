package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.dto.request.EventFilterRequest;
import com.as.eventalertbackend.dto.request.EventRequest;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.persistence.entity.EventSeverity;
import com.as.eventalertbackend.persistence.entity.EventTag;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.reopsitory.EventRepository;
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
    private final FileService fileService;
    private final NotificationService notificationService;

    @Autowired
    public EventService(EventRepository eventRepository,
                        EventSeverityService severityService,
                        EventTagService tagService,
                        UserService userService,
                        FileService fileService,
                        NotificationService notificationService) {
        this.eventRepository = eventRepository;
        this.severityService = severityService;
        this.tagService = tagService;
        this.userService = userService;
        this.fileService = fileService;
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
    public Page<Event> findByFilter(EventFilterRequest filterRequest, int pageSize, int pageNumber, Order order) {
        if (pageSize > AppConstants.MAX_PAGES) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_PAGE_SIZE);
        }

        if (filterRequest.getStartDate().isAfter(filterRequest.getEndDate())) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_END_DATE_AFTER_START_DATE);
        }

        if (filterRequest.getEndDate().getYear() - filterRequest.getStartDate().getYear() > AppConstants.MAX_YEARS_INTERVAL) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_YEARS_INTERVAL);
        }

        if (order == null) {
            order = Order.BY_DATE_DESCENDING;
        }

        LocalDateTime startDateTime = filterRequest.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = filterRequest.getEndDate().atTime(23, 59, 59);

        /*log.info("Starting events retrieval; latitude: {}, longitude: {}, radius: {}, start date: {}, end date: {}, tags: {}, severities: {}",
                filterRequest.getLatitude(), filterRequest.getLongitude(), filterRequest.getRadius(),
                startDateTime, endDateTime,
                filterRequest.getTagsIds(), filterRequest.getSeveritiesIds());*/

        List<EventRepository.DistanceProjection> distanceProjections = eventRepository.findByFilter(
                filterRequest.getLatitude(), filterRequest.getLongitude(), filterRequest.getRadius(),
                startDateTime, endDateTime,
                filterRequest.getTagsIds(), filterRequest.getSeveritiesIds());

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

        /*log.info("Retrieved total pages: {}, total elements: {}, events: {}",
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getContent().stream().mapToLong(Event::getId).toArray());*/

        return eventsPage;
    }

    public Event save(EventRequest eventRequest) {
        Event event = createOrUpdate(new Event(), eventRequest);
        notificationService.send(event);
        return event;
    }

    public Event updateById(EventRequest eventRequest, Long id) {
        return createOrUpdate(findById(id), eventRequest);
    }

    public void deleteById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND);
        }
    }

    private Event createOrUpdate(Event event, EventRequest eventRequest) {
        User user = userService.findById(eventRequest.getUserId());
        EventTag tag = tagService.findById(eventRequest.getTagId());
        EventSeverity severity = severityService.findById(eventRequest.getSeverityId());

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_FIRST_NAME_MANDATORY);
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_LAST_NAME_MANDATORY);
        }

        if (!fileService.imageExists(eventRequest.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        String description = eventRequest.getDescription() == null ?
                null : eventRequest.getDescription().replaceAll("\n", " ");

        event.setLatitude(eventRequest.getLatitude());
        event.setLongitude(eventRequest.getLongitude());
        event.setImagePath(eventRequest.getImagePath());
        event.setDescription(description);
        event.setSeverity(severity);
        event.setTag(tag);
        event.setUser(user);

        return eventRepository.save(event);
    }

}
