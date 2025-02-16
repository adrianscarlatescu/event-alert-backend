package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.dto.event.EventCreateDTO;
import com.as.eventalertbackend.dto.event.EventDTO;
import com.as.eventalertbackend.dto.event.EventFilterDTO;
import com.as.eventalertbackend.dto.event.EventUpdateDTO;
import com.as.eventalertbackend.dto.page.PageDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.model.OrderCode;
import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.persistence.entity.Severity;
import com.as.eventalertbackend.persistence.entity.Type;
import com.as.eventalertbackend.persistence.entity.User;
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
    private final UserService userService;
    private final FileService fileService;
    private final NotificationService notificationService;

    @Autowired
    public EventService(ModelMapper mapper,
                        EventRepository eventRepository,
                        SeverityService severityService,
                        TypeService typeService,
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

    public EventDTO findById(Long id) {
        return mapper.map(findEntityById(id), EventDTO.class);
    }

    public List<EventDTO> findAllByUserId(Long userId) {
        return eventRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(event -> mapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    public PageDTO<EventDTO> findByFilter(EventFilterDTO filterRequest, int pageSize, int pageNumber, OrderCode orderCode) {
        if (pageSize > AppConstants.MAX_PAGE_SIZE) {
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

        return new PageDTO<>(
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getContent().stream()
                        .map(event -> mapper.map(event, EventDTO.class))
                        .collect(Collectors.toList())
        );
    }

    public EventDTO save(EventCreateDTO eventCreateDTO) {
        Event event = createOrUpdate(eventCreateDTO, null);
        notificationService.send(event);
        return mapper.map(event, EventDTO.class);
    }

    public EventDTO updateById(EventUpdateDTO eventUpdateDTO, Long id) {
        Event event = createOrUpdate(eventUpdateDTO, id);
        return mapper.map(event, EventDTO.class);
    }

    private <T extends EventCreateDTO> Event createOrUpdate(T createOrUpdateDTO, Long eventId) {
        Event event = eventId == null ? new Event() : findEntityById(eventId);

        User user = userService.findEntityById(createOrUpdateDTO.getUserId());
        Type type = typeService.findEntityById(createOrUpdateDTO.getTypeId());
        Severity severity = severityService.findEntityById(createOrUpdateDTO.getSeverityId());

        if (user.getFirstName() == null || user.getLastName() == null) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_NAME_REQUIRED);
        }

        if (!fileService.imageExists(createOrUpdateDTO.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        event.setLatitude(createOrUpdateDTO.getLatitude());
        event.setLongitude(createOrUpdateDTO.getLongitude());
        event.setImagePath(createOrUpdateDTO.getImagePath());
        event.setDescription(createOrUpdateDTO.getDescription());
        event.setSeverity(severity);
        event.setType(type);
        event.setUser(user);

        return event;
    }

    public void deleteById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND);
        }
    }

}
