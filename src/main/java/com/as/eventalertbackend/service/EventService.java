package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventBody;
import com.as.eventalertbackend.controller.request.EventFilterBody;
import com.as.eventalertbackend.controller.response.PagedResponse;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.reopsitory.EventRepository;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.handler.exception.IllegalActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class EventService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final int MAX_RADIUS = 10_000;
    private static final int MAX_YEARS = 1;

    private final EventRepository repository;

    private final EventSeverityService eventSeverityService;
    private final EventTagService eventTagService;
    private final UserService userService;

    @Autowired
    public EventService(EventRepository repository,
                        EventSeverityService eventSeverityService,
                        EventTagService eventTagService,
                        UserService userService) {
        this.repository = repository;
        this.eventSeverityService = eventSeverityService;
        this.eventTagService = eventTagService;
        this.userService = userService;
    }

    public Event findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No record for event " + id,
                        "Event not found"));
    }

    public List<Event> findByUserId(Long userId) {
        return repository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Transactional
    public PagedResponse<Event> findByFilter(EventFilterBody body, int pageSize, int pageNumber, Order order) {
        if (pageSize > MAX_PAGE_SIZE) {
            throw new IllegalActionException(
                    "The page size " + pageNumber + " is greater than maximum allowed " + MAX_PAGE_SIZE,
                    "The page size must be less than " + MAX_PAGE_SIZE);
        }

        if (body.getStartDate().isAfter(body.getEndDate())) {
            throw new IllegalActionException(
                    "Start date " + body.getStartDate().toString() + " is after end date " + body.getEndDate().toString(),
                    "The end date must be after the start date");
        }

        if (body.getEndDate().getYear() - body.getStartDate().getYear() >= MAX_YEARS) {
            throw new IllegalActionException(
                    "The difference between the start year " + body.getStartDate().getYear() +
                            " and the end year " + body.getEndDate().getYear() +
                            " is greater than maximum allowed " + MAX_YEARS,
                    "The years interval must be maximum " + MAX_YEARS);
        }

        if (body.getRadius() > MAX_RADIUS) {
            throw new IllegalActionException(
                    "Radius " + body.getRadius() + " greater than maximum allowed " + MAX_RADIUS,
                    "The radius must be less than " + MAX_RADIUS);
        }

        if (order == null) {
            order = Order.BY_DATE_DESCENDING;
        }

        LocalDateTime startDateTime = body.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = body.getEndDate().atTime(23, 59, 59);

        log.info("Starting events retrieval; latitude: {}, longitude: {}, radius: {}, start date: {}, end date: {}, tags: {}, severities: {}",
                body.getLatitude(), body.getLongitude(), body.getRadius(),
                startDateTime, endDateTime,
                body.getTagsIds(), body.getSeveritiesIds());

        List<EventRepository.EventProjection> eventProjections = repository.findByFilter(
                body.getLatitude(), body.getLongitude(), body.getRadius(),
                startDateTime, endDateTime,
                body.getTagsIds(), body.getSeveritiesIds());

        if (order == Order.BY_DISTANCE_DESCENDING) {
            Collections.reverse(eventProjections);
        }

        long[] eventsIds = eventProjections.stream()
                .mapToLong(EventRepository.EventProjection::getId)
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
            case BY_DISTANCE_ASCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, JpaSort.unsafe("field(id, ?1)"));
                break;
            case BY_DISTANCE_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, JpaSort.unsafe("field(id, ?1)"));
                break;
        }

        Page<Event> eventPages = repository.findByFilter(
                eventsIds,
                startDateTime, endDateTime,
                body.getTagsIds(), body.getSeveritiesIds(),
                pageRequest
        );

        eventPages.get().forEach(event ->
                eventProjections.stream()
                        .filter(eventProjection -> eventProjection.getId().longValue() == event.getId().longValue())
                        .findFirst()
                        .ifPresent(eventProjection -> event.setDistance(eventProjection.getDistance()))
        );

        log.info("Result: {}", eventPages.getContent().toString());

        PagedResponse<Event> pagedResponse = new PagedResponse<>();
        pagedResponse.setTotalPages(eventPages.getTotalPages());
        pagedResponse.setTotalElements(eventPages.getTotalElements());
        pagedResponse.setContent(eventPages.getContent());

        return pagedResponse;
    }

    public Event save(Event event) {
        return repository.save(event);
    }

    public Event save(EventBody body) {
        return save(new Event(), body);
    }

    public Event updateById(EventBody body, Long id) {
        Event dbObj = findById(id);
        return save(dbObj, body);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException(
                    "No record for event " + id,
                    "Event not found");
        }
    }

    private Event save(Event dbObj, EventBody body) {
        User user = userService.findById(body.getUserId());
        EventTag tag = eventTagService.findById(body.getTagId());
        EventSeverity severity = eventSeverityService.findById(body.getSeverityId());

        List<String> descriptions = new ArrayList<>();
        boolean isFirstNameInvalid = isNullOrEmpty(user.getFirstName());
        boolean isLastNameInvalid = isNullOrEmpty(user.getLastName());
        if (isFirstNameInvalid) {
            descriptions.add("The first name is mandatory");
        }
        if (isLastNameInvalid) {
            descriptions.add("The last name is mandatory");
        }

        if (!descriptions.isEmpty()) {
            String message = "Could not create the event, missing user " + user.getId() + " mandatory data";
            throw new IllegalActionException(message, descriptions);
        }

        String description = body.getDescription() == null ?
                null : body.getDescription().replaceAll("\n", " ");

        dbObj.setLatitude(body.getLatitude());
        dbObj.setLongitude(body.getLongitude());
        dbObj.setImagePath(body.getImagePath());
        dbObj.setDescription(description);
        dbObj.setSeverity(severity);
        dbObj.setTag(tag);
        dbObj.setUser(user);

        return repository.save(dbObj);
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
