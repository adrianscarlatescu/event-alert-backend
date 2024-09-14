package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.controller.request.EventFilterRequestDto;
import com.as.eventalertbackend.controller.request.EventRequestDto;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.reopsitory.EventRepository;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.notification.enabled}")
    private boolean isNotificationEnabled;

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
                .orElseThrow(() -> new RecordNotFoundException("Event not found"));
    }

    public List<Event> findAllByUserId(Long userId) {
        return eventRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Transactional
    public Page<Event> findByFilter(EventFilterRequestDto filterRequestDto, int pageSize, int pageNumber, Order order) {
        if (pageSize > AppConstants.MAX_PAGES) {
            throw new InvalidActionException("The page size must be less than " + AppConstants.MAX_PAGES);
        }

        if (filterRequestDto.getStartDate().isAfter(filterRequestDto.getEndDate())) {
            throw new InvalidActionException("The end date must be after the start date");
        }

        if (filterRequestDto.getEndDate().getYear() - filterRequestDto.getStartDate().getYear() > AppConstants.MAX_YEARS_INTERVAL) {
            throw new InvalidActionException("The years interval must be maximum " + AppConstants.MAX_YEARS_INTERVAL);
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

        log.info("Retrieved pages: {}, elements: {}, events: {}",
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getContent().stream().mapToLong(Event::getId).toArray());

        return eventsPage;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event save(EventRequestDto eventRequestDto) {
        return save(new Event(), eventRequestDto);
    }

    public Event updateById(EventRequestDto eventRequestDto, Long id) {
        Event event = findById(id);
        return save(event, eventRequestDto);
    }

    public void deleteById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Event not found");
        }
    }

    private Event save(Event event, EventRequestDto eventRequestDto) {
        User user = userService.findById(eventRequestDto.getUserId());
        EventTag tag = tagService.findById(eventRequestDto.getTagId());
        EventSeverity severity = severityService.findById(eventRequestDto.getSeverityId());

        if (isNullOrEmpty(user.getFirstName())) {
            throw new InvalidActionException("User first name is mandatory");
        }
        if (isNullOrEmpty(user.getLastName())) {
            throw new InvalidActionException("User last name is mandatory");
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

        Event newEvent = eventRepository.save(event);

        if (isNotificationEnabled) {
            notificationService.send(newEvent);
        }

        return newEvent;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
