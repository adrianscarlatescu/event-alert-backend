package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.dto.event.EventCreateDTO;
import com.as.eventalertbackend.dto.event.EventDTO;
import com.as.eventalertbackend.dto.event.EventUpdateDTO;
import com.as.eventalertbackend.dto.event.EventsFilterDTO;
import com.as.eventalertbackend.dto.page.PageDTO;
import com.as.eventalertbackend.enums.EventsOrder;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.persistence.entity.*;
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

    private final SeverityService severityService;
    private final TypeService typeService;
    private final StatusService statusService;
    private final UserService userService;
    private final FileService fileService;
    private final NotificationService notificationService;

    @Autowired
    public EventService(ModelMapper mapper,
                        EventRepository eventRepository,
                        SeverityService severityService,
                        TypeService typeService,
                        StatusService statusService,
                        UserService userService,
                        FileService fileService,
                        NotificationService notificationService) {
        this.mapper = mapper;
        this.eventRepository = eventRepository;
        this.severityService = severityService;
        this.typeService = typeService;
        this.statusService = statusService;
        this.userService = userService;
        this.fileService = fileService;
        this.notificationService = notificationService;
    }

    Event findEntityById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND));
    }

    public EventDTO findById(Long id) {
        return mapper.map(findEntityById(id), EventDTO.class);
    }

    public List<EventDTO> findAllByUserId(Long userId) {
        return eventRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(event -> mapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    public PageDTO<EventDTO> findByFilter(EventsFilterDTO eventsFilterDTO, int pageSize, int pageNumber, EventsOrder eventsOrder) {
        if (pageSize > AppConstants.MAX_PAGE_SIZE) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_PAGE_SIZE);
        }

        if (eventsFilterDTO.getStartDate().isAfter(eventsFilterDTO.getEndDate())) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_END_DATE_AFTER_START_DATE);
        }

        if (eventsFilterDTO.getEndDate().getYear() - eventsFilterDTO.getStartDate().getYear() > AppConstants.MAX_YEARS_INTERVAL) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_YEARS_INTERVAL);
        }

        LocalDateTime startDateTime = eventsFilterDTO.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = eventsFilterDTO.getEndDate().atTime(23, 59, 59);

        List<EventProjection> eventProjections = eventRepository.findByFilter(
                eventsFilterDTO.getLatitude(),
                eventsFilterDTO.getLongitude(),
                eventsFilterDTO.getRadius(),
                startDateTime,
                endDateTime,
                eventsFilterDTO.getTypeIds(),
                eventsFilterDTO.getSeverityIds(),
                eventsFilterDTO.getStatusIds()
        );

        if (eventsOrder == EventsOrder.BY_DISTANCE_DESCENDING) {
            Collections.reverse(eventProjections);
        }

        long[] eventIds = eventProjections.stream()
                .mapToLong(EventProjection::getId)
                .toArray();

        PageRequest pageRequest;
        switch (eventsOrder) {
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
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("severity_position"));
                break;
            case BY_SEVERITY_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("severity_position").descending());
                break;
            case BY_IMPACT_RADIUS_ASCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("impact_radius"));
                break;
            case BY_IMPACT_RADIUS_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("impact_radius").descending());
                break;
            case BY_DISTANCE_ASCENDING, BY_DISTANCE_DESCENDING:
                pageRequest = PageRequest.of(pageNumber, pageSize, JpaSort.unsafe("field(e.id, :eventIds)"));
                break;
        }

        Page<Event> eventsPage = eventRepository.findByIds(eventIds, pageRequest);

        eventsPage.get().forEach(event ->
                eventProjections.stream()
                        .filter(eventProjection -> eventProjection.getId().longValue() == event.getId().longValue())
                        .findFirst()
                        .ifPresent(eventProjection -> event.setDistance(eventProjection.getDistance()))
        );

        return new PageDTO<>(
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getContent().stream()
                        .map(event -> mapper.map(event, EventDTO.class))
                        .collect(Collectors.toList())
        );
    }

    public EventDTO save(EventCreateDTO eventCreateDTO) {
        Event newEvent = new Event();

        Type type = typeService.findEntityById(eventCreateDTO.getTypeId());
        Severity severity = severityService.findEntityById(eventCreateDTO.getSeverityId());
        Status status = statusService.findEntityById(eventCreateDTO.getStatusId());
        User user = userService.findEntityById(eventCreateDTO.getUserId());

        if (user.getFirstName() == null || user.getLastName() == null) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_FULL_NAME_MANDATORY);
        }

        if (!fileService.imageExists(eventCreateDTO.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        newEvent.setLatitude(eventCreateDTO.getLatitude());
        newEvent.setLongitude(eventCreateDTO.getLongitude());
        newEvent.setImpactRadius(eventCreateDTO.getImpactRadius());
        newEvent.setType(type);
        newEvent.setSeverity(severity);
        newEvent.setStatus(status);
        newEvent.setUser(user);
        newEvent.setImagePath(eventCreateDTO.getImagePath());
        newEvent.setDescription(eventCreateDTO.getDescription());

        Event event = eventRepository.save(newEvent);

        notificationService.send(event);

        return mapper.map(event, EventDTO.class);
    }

    public EventDTO updateById(EventUpdateDTO eventStatusUpdateDTO, Long id) {
        Event event = findEntityById(id);

        Type type = typeService.findEntityById(eventStatusUpdateDTO.getTypeId());
        Severity severity = severityService.findEntityById(eventStatusUpdateDTO.getSeverityId());
        Status status = statusService.findEntityById(eventStatusUpdateDTO.getStatusId());

        event.setImpactRadius(eventStatusUpdateDTO.getImpactRadius());
        event.setType(type);
        event.setSeverity(severity);
        event.setStatus(status);
        event.setImagePath(eventStatusUpdateDTO.getImagePath());
        event.setDescription(eventStatusUpdateDTO.getDescription());

        return mapper.map(event, EventDTO.class);
    }

    public void deleteById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND);
        }
    }

}
