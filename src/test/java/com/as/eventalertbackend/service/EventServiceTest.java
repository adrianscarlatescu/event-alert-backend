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

    private Long eventId = 1L;
    private Long userId = 1L;
    private Long tagId = 1L;
    private Long severityId = 1L;
    private String imagePath = "imagePath";
    private Double latitude = 44.4555611;
    private Double longitude = 26.0404115;
    private String description = "description";
    private int radius = 100;
    private LocalDate startDate = LocalDate.of(2020, Month.APRIL, 1);
    private LocalDate endDate = LocalDate.of(2021, Month.APRIL, 1);
    private long[] tagsIds = {1L};
    private long[] severitiesIds = {1L};

    @Test
    public void shouldFindById() {
        // given
        Long id = 1L;
        Event mockEvent = new Event();
        mockEvent.setId(id);

        given(eventRepository.findById(id)).willReturn(Optional.of(mockEvent));

        // when
        Event event = eventService.findById(id);

        // then
        assertNotNull(event);
        assertEquals(id, event.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(eventRepository.findById(any())).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> eventService.findById(any()));
    }

    @Test
    public void shouldNotFindByFilterForMaxPageSize() {
        // given
        EventFilterBody body = getMockEventFilterBody();
        int pageSize = 101;
        int pageNumber = 0;
        Order order = Order.BY_DATE_DESCENDING;

        // when
        // then
        assertThrows(IllegalActionException.class,
                () -> eventService.findByFilter(body, pageSize, pageNumber, order));
    }

    @Test
    public void shouldNotFindByFilterForStartDateAfterEndDate() {
        // given
        EventFilterBody body = getMockEventFilterBody();
        body.setStartDate(LocalDate.of(2021, Month.JULY, 1));

        int pageSize = 20;
        int pageNumber = 0;
        Order order = Order.BY_DATE_DESCENDING;

        // when
        // then
        assertThrows(IllegalActionException.class,
                () -> eventService.findByFilter(body, pageSize, pageNumber, order));
    }

    @Test
    public void shouldNotFindByFilterForMaxDateDifference() {
        // given
        EventFilterBody body = getMockEventFilterBody();
        body.setEndDate(LocalDate.of(2022, Month.JULY, 1));

        int pageSize = 20;
        int pageNumber = 0;
        Order order = Order.BY_DATE_DESCENDING;

        // when
        // then
        assertThrows(IllegalActionException.class,
                () -> eventService.findByFilter(body, pageSize, pageNumber, order));
    }

    @Test
    public void shouldNotFindByFilterForMaxRadius() {
        // given
        EventFilterBody body = getMockEventFilterBody();
        body.setRadius(20000);

        int pageSize = 20;
        int pageNumber = 0;
        Order order = Order.BY_DATE_DESCENDING;

        // when
        // then
        assertThrows(IllegalActionException.class,
                () -> eventService.findByFilter(body, pageSize, pageNumber, order));
    }

    @Test
    public void shouldFindByFilter() {
        // given
        EventFilterBody body = getMockEventFilterBody();

        int pageSize = 20;
        int pageNumber = 0;
        Order order = Order.BY_DISTANCE_DESCENDING;

        List<EventRepository.EventProjection> eventProjections = getMockEventProjections();

        given(eventRepository.findByFilter(anyDouble(), anyDouble(), anyInt(), any(), any(), any(), any()))
                .willReturn(eventProjections);

        List<Event> events = getMockEvents();
        Page<Event> eventPages = new PageImpl(events);

        given(eventRepository.findByFilter(any(), any(), any(), any(), any(), any()))
                .willReturn(eventPages);

        // when
        PagedResponse<Event> response = eventService.findByFilter(body, pageSize, pageNumber, order);

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
        Long id = 1L;
        Event mockEvent = new Event();
        mockEvent.setId(id);

        given(eventRepository.save(mockEvent)).willReturn(mockEvent);

        // when
        Event event = eventService.save(mockEvent);

        // then
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(argumentCaptor.capture());

        Event capturedEvent = argumentCaptor.getValue();

        assertEquals(mockEvent, capturedEvent);
        assertNotNull(event);
        assertEquals(id, event.getId());
    }

    @Test
    public void shouldSaveNewEvent() {
        // given
        EventBody newEventBody = getMockEventBody();
        Event mockEvent = getMockEvent();

        given(userService.findById(userId)).willReturn(mockEvent.getUser());
        given(eventTagService.findById(tagId)).willReturn(mockEvent.getTag());
        given(eventSeverityService.findById(severityId)).willReturn(mockEvent.getSeverity());
        given(eventRepository.save(any())).willReturn(mockEvent);

        // when
        Event event = eventService.save(newEventBody);

        // then
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(argumentCaptor.capture());

        Event capturedEvent = argumentCaptor.getValue();

        assertNotNull(event);
        assertEquals(eventId, event.getId());
        assertEquals(userId, capturedEvent.getUser().getId());
        assertEquals(tagId, capturedEvent.getTag().getId());
        assertEquals(severityId, capturedEvent.getSeverity().getId());
        assertEquals(latitude, capturedEvent.getLatitude());
        assertEquals(longitude, capturedEvent.getLongitude());
        assertEquals(imagePath, capturedEvent.getImagePath());
        assertEquals(description, capturedEvent.getDescription());
    }

    @Test
    public void shouldNotSaveNewEventWithoutUserData() {
        // given
        EventBody newEventBody = getMockEventBody();
        Event mockEvent = getMockEvent();

        User mockUser = mockEvent.getUser();
        mockUser.setFirstName(null);
        mockUser.setLastName(null);

        given(userService.findById(userId)).willReturn(mockEvent.getUser());
        given(eventTagService.findById(tagId)).willReturn(mockEvent.getTag());
        given(eventSeverityService.findById(severityId)).willReturn(mockEvent.getSeverity());

        // when
        // then
        assertThrows(IllegalActionException.class, () -> eventService.save(newEventBody));
        verify(eventRepository, times(0)).save(any());
    }

    @Test
    public void shouldDeleteById() {
        // given
        Long id = 1L;
        given(eventRepository.existsById(id)).willReturn(true);

        // when
        eventService.deleteById(id);

        // then
        verify(eventRepository).deleteById(id);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        Long id = 1L;
        given(eventRepository.existsById(id)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> eventService.deleteById(id));
        verify(eventRepository, times(0)).deleteById(id);
    }

    private EventFilterBody getMockEventFilterBody() {
        EventFilterBody body = new EventFilterBody();
        body.setRadius(radius);
        body.setStartDate(startDate);
        body.setEndDate(endDate);
        body.setLatitude(latitude);
        body.setLongitude(longitude);
        body.setTagsIds(tagsIds);
        body.setSeveritiesIds(severitiesIds);
        return body;
    }

    private EventBody getMockEventBody() {
        EventBody body = new EventBody();
        body.setUserId(userId);
        body.setTagId(tagId);
        body.setSeverityId(severityId);
        body.setImagePath(imagePath);
        body.setLatitude(latitude);
        body.setLongitude(longitude);
        body.setDescription(description);
        return body;
    }

    private Event getMockEvent() {
        Event event = new Event();
        event.setId(eventId);
        event.setUser(getMockUser());
        event.setTag(getMockTag());
        event.setSeverity(getMockSeverity());
        event.setLatitude(latitude);
        event.setLongitude(longitude);
        event.setImagePath(imagePath);
        event.setDescription(description);
        return event;
    }

    private EventTag getMockTag() {
        EventTag tag = new EventTag();
        tag.setId(1L);
        tag.setName("name");
        tag.setImagePath("imagePath");
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

    private List<EventRepository.EventProjection> getMockEventProjections() {
        List<EventRepository.EventProjection> eventProjections = new ArrayList<>();
        for (int i = 0; i < MOCK_EVENTS_NUMBER; i++) {
            final long index = i + 1;
            eventProjections.add(new EventRepository.EventProjection() {
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
        return eventProjections;
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
