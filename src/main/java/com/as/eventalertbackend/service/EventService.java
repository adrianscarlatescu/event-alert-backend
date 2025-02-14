package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.dto.request.EventFilterRequest;
import com.as.eventalertbackend.dto.response.EventResponse;
import com.as.eventalertbackend.dto.response.PagedResponse;
import com.as.eventalertbackend.model.OrderCode;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.persistence.projection.EventProjection;
import com.as.eventalertbackend.persistence.reopsitory.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class EventService {

    private final ModelMapper mapper;

    private final EventRepository eventRepository;

    private final EventSeverityService severityService;
    private final EventTypeService typeService;
    private final UserService userService;
    private final FileService fileService;
    private final NotificationService notificationService;

    @Autowired
    public EventService(ModelMapper mapper,
                        EventRepository eventRepository,
                        EventSeverityService severityService,
                        EventTypeService typeService,
                        UserService userService,
                        FileService fileService,
                        NotificationService notificationService) {
        this.mapper = mapper;
        this.eventRepository = eventRepository;
        this.severityService = severityService;
        this.typeService = typeService;
        this.userService = userService;
        this.fileService = fileService;
        this.notificationService = notificationService;
    }

    Event findEntityById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND));
    }

    public EventResponse findById(Long id) {
        return mapper.map(findEntityById(id), EventResponse.class);
    }

    public List<EventResponse> findAllByUserId(Long userId) {
        return eventRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(event -> mapper.map(event, EventResponse.class))
                .collect(Collectors.toList());
    }

    public PagedResponse<EventResponse> findByFilter(EventFilterRequest filterRequest, int pageSize, int pageNumber, OrderCode orderCode) {
        if (pageSize > AppConstants.MAX_PAGES) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_PAGE_SIZE);
        }

        if (filterRequest.getStartDate().isAfter(filterRequest.getEndDate())) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_END_DATE_AFTER_START_DATE);
        }

        if (filterRequest.getEndDate().getYear() - filterRequest.getStartDate().getYear() > AppConstants.MAX_YEARS_INTERVAL) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_YEARS_INTERVAL);
        }

        if (orderCode == null) {
            orderCode = OrderCode.BY_DATE_DESCENDING;
        }

        LocalDateTime startDateTime = filterRequest.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = filterRequest.getEndDate().atTime(23, 59, 59);

        List<EventProjection> eventProjections = eventRepository.findByFilter(
                filterRequest.getLatitude(),
                filterRequest.getLongitude(),
                filterRequest.getRadius(),
                startDateTime,
                endDateTime,
                filterRequest.getTypeIds(),
                filterRequest.getSeverityIds()
        );

        if (orderCode == OrderCode.BY_DISTANCE_DESCENDING) {
            Collections.reverse(eventProjections);
        }

        long[] eventIds = eventProjections.stream()
                .mapToLong(EventProjection::getId)
                .toArray();

        PageRequest pageRequest;
        switch (orderCode) {
            default:
                pageRequest = PageRequest.of(pageNumber, pageSize);
                break;
            case BY_DATE_ASCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("created_at"));
                break;
            case BY_DATE_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("created_at").descending());
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

        Page<Event> eventsPage = eventRepository.findByIds(eventIds, pageRequest);

        eventsPage.get().forEach(event ->
                eventProjections.stream()
                        .filter(eventProjection -> eventProjection.getId().longValue() == event.getId().longValue())
                        .findFirst()
                        .ifPresent(eventProjection -> event.setDistance(eventProjection.getDistance()))
        );

        return new PagedResponse<>(
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getContent().stream()
                        .map(event -> mapper.map(event, EventResponse.class))
                        .collect(Collectors.toList())
        );
    }

    /*public Event save(EventRequest eventRequest) {
        Event event = eventRepository.save(createOrUpdate(new Event(), eventRequest));
        notificationService.send(event);
        return event;
    }

    public Event updateById(EventRequest eventRequest, Long id) {
        return createOrUpdate(findEntityById(id), eventRequest);
    }*/

    public void deleteById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND);
        }
    }

    /*private Event createOrUpdate(Event event, EventRequest eventRequest) {
        User user = userService.findEntityById(eventRequest.getUserId());
        EventType type = tagService.findEntityById(eventRequest.getTypeId());
        EventSeverity severity = severityService.findEntityById(eventRequest.getSeverityId());

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_FIRST_NAME_MANDATORY);
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_LAST_NAME_MANDATORY);
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_PHONE_NUMBER_MANDATORY);
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
        event.setType(type);
        event.setUser(user);

        return event;
    }*/

}
