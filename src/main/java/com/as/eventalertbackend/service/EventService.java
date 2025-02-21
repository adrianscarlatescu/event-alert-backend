package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.dto.event.*;
import com.as.eventalertbackend.dto.page.PageDTO;
import com.as.eventalertbackend.enums.OrderCode;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.persistence.entity.*;
import com.as.eventalertbackend.persistence.entity.lookup.Severity;
import com.as.eventalertbackend.persistence.entity.lookup.Status;
import com.as.eventalertbackend.persistence.entity.lookup.Type;
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

    public PageDTO<EventBaseDTO> findByFilter(EventFilterDTO eventFilterDTO, int pageSize, int pageNumber, OrderCode orderCode) {
        if (pageSize > AppConstants.MAX_PAGE_SIZE) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_PAGE_SIZE);
        }

        if (eventFilterDTO.getStartDate().isAfter(eventFilterDTO.getEndDate())) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_END_DATE_AFTER_START_DATE);
        }

        if (eventFilterDTO.getEndDate().getYear() - eventFilterDTO.getStartDate().getYear() > AppConstants.MAX_YEARS_INTERVAL) {
            throw new InvalidActionException(ApiErrorMessage.FILTER_MAX_YEARS_INTERVAL);
        }

        if (orderCode == null) {
            orderCode = OrderCode.BY_DATE_DESCENDING;
        }

        LocalDateTime startDateTime = eventFilterDTO.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = eventFilterDTO.getEndDate().atTime(23, 59, 59);

        List<EventProjection> eventProjections = eventRepository.findByFilter(
                eventFilterDTO.getLatitude(),
                eventFilterDTO.getLongitude(),
                eventFilterDTO.getRadius(),
                startDateTime,
                endDateTime,
                eventFilterDTO.getTypeIds(),
                eventFilterDTO.getSeverityIds()
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

        return new PageDTO<>(
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getContent().stream()
                        .map(event -> mapper.map(event, EventBaseDTO.class))
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
        newEvent.setSeverity(severity);
        newEvent.setType(type);
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
        event.setSeverity(severity);
        event.setType(type);
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
