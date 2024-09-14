package com.as.eventalertbackend.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventSeverityService eventSeverityService;
    @Mock
    private EventTagService eventTagService;
    @Mock
    private UserService userService;

    @InjectMocks
    private EventService eventService;

    private static final int MOCK_EVENTS_NUMBER = 3;

    private final Long eventId = 1L;
    private final String eventImagePath = "img/event_1.png";
    private final Double eventLatitude = 44.4555611;
    private final Double eventLongitude = 26.0404115;
    private final String eventDescription = "test";
    private final int radius = 100;
    private final LocalDate eventStartDate = LocalDate.of(2020, Month.APRIL, 1);
    private final LocalDate eventEndDate = LocalDate.of(2021, Month.APRIL, 1);
    private final long[] eventTagsIds = {1L};
    private final long[] eventSeveritiesIds = {1L};

    private final Long userId = 1L;
    private final Long tagId = 1L;
    private final Long severityId = 1L;

    @Test
    public void shouldFindById() {
        // given
        Event mockEvent = new Event();
        mockEvent.setId(eventId);

        given(eventRepository.findById(eventId)).willReturn(Optional.of(mockEvent));

        // when
        Event event = eventService.findById(eventId);

        // then
        assertNotNull(event);
        assertEquals(eventId, event.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(eventRepository.findById(eventId)).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> eventService.findById(eventId));
    }

    @Test
    public void shouldNotFindByFilterForMaxPageSize() {
        // given
        EventFilterRequestDto filterRequestDto = getMockEventFilterRequestDto();
        int pageSize = 101;
        int pageNumber = 0;
        Order order = Order.BY_DATE_DESCENDING;

        // when
        // then
        assertThrows(InvalidActionException.class,
                () -> eventService.findByFilter(filterRequestDto, pageSize, pageNumber, order));
    }

    @Test
    public void shouldNotFindByFilterForStartDateAfterEndDate() {
        // given
        EventFilterRequestDto filterRequestDto = getMockEventFilterRequestDto();
        filterRequestDto.setStartDate(LocalDate.of(2021, Month.JULY, 1));

        int pageSize = 20;
        int pageNumber = 0;
        Order order = Order.BY_DATE_DESCENDING;

        // when
        // then
        assertThrows(InvalidActionException.class,
                () -> eventService.findByFilter(filterRequestDto, pageSize, pageNumber, order));
    }

    @Test
    public void shouldNotFindByFilterForMaxDateDifference() {
        // given
        EventFilterRequestDto filterRequestDto = getMockEventFilterRequestDto();
        filterRequestDto.setEndDate(LocalDate.of(2022, Month.JULY, 1));

        int pageSize = 20;
        int pageNumber = 0;
        Order order = Order.BY_DATE_DESCENDING;

        // when
        // then
        assertThrows(InvalidActionException.class,
                () -> eventService.findByFilter(filterRequestDto, pageSize, pageNumber, order));
    }

    @Test
    public void shouldFindByFilter() {
        // given
        EventFilterRequestDto filterRequestDto = getMockEventFilterRequestDto();

        int pageSize = 20;
        int pageNumber = 0;
        Order order = Order.BY_DISTANCE_DESCENDING;

        List<EventRepository.DistanceProjection> distanceProjections = getMockDistanceProjections();

        given(eventRepository.findByFilter(anyDouble(), anyDouble(), anyInt(), any(), any(), any(), any()))
                .willReturn(distanceProjections);

        List<Event> events = getMockEvents();
        Page<Event> eventPages = new PageImpl<>(events);

        given(eventRepository.findByIds(any(), any())).willReturn(eventPages);

        // when
        Page<Event> response = eventService.findByFilter(filterRequestDto, pageSize, pageNumber, order);

        // then
        assertNotNull(response);
        assertEquals(MOCK_EVENTS_NUMBER, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertTrue(IntStream
                .range(0, MOCK_EVENTS_NUMBER - 1)
                .allMatch(i -> Double.compare(
                        response.getContent().get(i).getDistance(),
                        response.getContent().get(i + 1).getDistance()) > 0)
        );
    }


    @Test
    public void shouldSave() {
        // given
        Event mockEvent = new Event();
        mockEvent.setId(eventId);

        given(eventRepository.save(any())).willReturn(mockEvent);

        // when
        Event event = eventService.save(mockEvent);

        // then
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(argumentCaptor.capture());

        Event capturedEvent = argumentCaptor.getValue();

        assertEquals(mockEvent, capturedEvent);
        assertNotNull(event);
        assertEquals(eventId, event.getId());
    }

    @Test
    public void shouldSaveNewEvent() {
        // given
        // Notification variable is already false
        EventRequestDto eventRequestDto = getMockEventRequestDto();
        Event mockEvent = getMockEvent();

        given(userService.findById(userId)).willReturn(mockEvent.getUser());
        given(eventTagService.findById(tagId)).willReturn(mockEvent.getTag());
        given(eventSeverityService.findById(severityId)).willReturn(mockEvent.getSeverity());
        given(eventRepository.save(any())).willReturn(mockEvent);

        // when
        Event event = eventService.save(eventRequestDto);

        // then
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(argumentCaptor.capture());

        Event capturedEvent = argumentCaptor.getValue();

        assertNotNull(event);
        assertEquals(eventId, event.getId());
        assertEquals(userId, capturedEvent.getUser().getId());
        assertEquals(tagId, capturedEvent.getTag().getId());
        assertEquals(severityId, capturedEvent.getSeverity().getId());
        assertEquals(eventLatitude, capturedEvent.getLatitude());
        assertEquals(eventLongitude, capturedEvent.getLongitude());
        assertEquals(eventImagePath, capturedEvent.getImagePath());
        assertEquals(eventDescription, capturedEvent.getDescription());
    }

    @Test
    public void shouldNotSaveNewEventWithoutUserData() {
        // given
        EventRequestDto eventRequestDto = getMockEventRequestDto();
        Event mockEvent = getMockEvent();

        User mockUser = mockEvent.getUser();
        mockUser.setFirstName(null);
        mockUser.setLastName(null);

        given(userService.findById(userId)).willReturn(mockEvent.getUser());
        given(eventTagService.findById(tagId)).willReturn(mockEvent.getTag());
        given(eventSeverityService.findById(severityId)).willReturn(mockEvent.getSeverity());

        // when
        // then
        assertThrows(InvalidActionException.class, () -> eventService.save(eventRequestDto));
        verify(eventRepository, times(0)).save(any());
    }

    @Test
    public void shouldDeleteById() {
        // given
        given(eventRepository.existsById(eventId)).willReturn(true);

        // when
        eventService.deleteById(eventId);

        // then
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        given(eventRepository.existsById(eventId)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> eventService.deleteById(eventId));
        verify(eventRepository, times(0)).deleteById(eventId);
    }

    private EventFilterRequestDto getMockEventFilterRequestDto() {
        EventFilterRequestDto filterRequestDto = new EventFilterRequestDto();
        filterRequestDto.setRadius(radius);
        filterRequestDto.setStartDate(eventStartDate);
        filterRequestDto.setEndDate(eventEndDate);
        filterRequestDto.setLatitude(eventLatitude);
        filterRequestDto.setLongitude(eventLongitude);
        filterRequestDto.setTagsIds(eventTagsIds);
        filterRequestDto.setSeveritiesIds(eventSeveritiesIds);
        return filterRequestDto;
    }

    private EventRequestDto getMockEventRequestDto() {
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setUserId(userId);
        eventRequestDto.setTagId(tagId);
        eventRequestDto.setSeverityId(severityId);
        eventRequestDto.setImagePath(eventImagePath);
        eventRequestDto.setLatitude(eventLatitude);
        eventRequestDto.setLongitude(eventLongitude);
        eventRequestDto.setDescription(eventDescription);
        return eventRequestDto;
    }

    private Event getMockEvent() {
        Event event = new Event();
        event.setId(eventId);
        event.setUser(getMockUser());
        event.setTag(getMockTag());
        event.setSeverity(getMockSeverity());
        event.setLatitude(eventLatitude);
        event.setLongitude(eventLongitude);
        event.setImagePath(eventImagePath);
        event.setDescription(eventDescription);
        return event;
    }

    private EventTag getMockTag() {
        EventTag tag = new EventTag();
        tag.setId(1L);
        tag.setName("name");
        tag.setImagePath("img/tag_1.png");
        return tag;
    }

    private EventSeverity getMockSeverity() {
        EventSeverity severity = new EventSeverity();
        severity.setId(1L);
        severity.setName("name");
        severity.setColor(0);
        return severity;
    }

    private User getMockUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        return user;
    }

    private List<EventRepository.DistanceProjection> getMockDistanceProjections() {
        List<EventRepository.DistanceProjection> distanceProjections = new ArrayList<>();
        for (int i = 0; i < MOCK_EVENTS_NUMBER; i++) {
            final long index = i + 1;
            distanceProjections.add(new EventRepository.DistanceProjection() {
                @Override
                public Long getId() {
                    return index;
                }

                @Override
                public Double getDistance() {
                    return (double) index * 30;
                }
            });
        }
        return distanceProjections;
    }

    private List<Event> getMockEvents() {
        List<Event> events = new ArrayList<>();

        for (int i = MOCK_EVENTS_NUMBER; i > 0; i--) {
            Event event = getMockEvent();
            event.setId((long) i);
            events.add(event);
        }

        return events;
    }

}
