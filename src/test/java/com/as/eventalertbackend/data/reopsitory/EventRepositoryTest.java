package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.data.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventSeverityRepository severityRepository;
    @Autowired
    private EventTagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    private static final int MOCK_EVENTS_NUMBER = 2;

    private Long userId;
    private Long tagId;
    private Long severityId;

    @BeforeEach
    void setUp() {
        User user = new User();
        User savedUser = userRepository.save(user);
        userId = savedUser.getId();

        EventTag tag = new EventTag();
        tag.setName("test");
        tag.setImagePath("/path");
        EventTag savedTag = tagRepository.save(tag);
        tagId = tag.getId();

        EventSeverity severity = new EventSeverity();
        severity.setName("test");
        EventSeverity savedSeverity = severityRepository.save(severity);
        severityId = severity.getId();

        double latitude = 44.4555611;
        double longitude = 26.0404115;

        for (int i = 0; i < MOCK_EVENTS_NUMBER; i++) {
            Event event = new Event();
            event.setUser(savedUser);
            event.setTag(savedTag);
            event.setSeverity(savedSeverity);
            event.setLatitude(latitude);
            event.setLongitude(longitude);
            Event savedEvent = eventRepository.save(event);
            // Update creation timestamp
            savedEvent.setDateTime(LocalDateTime.of(2020 + i, Month.SEPTEMBER, 1, 10, 30, 0));
            eventRepository.save(savedEvent);
        }

    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
        severityRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    public void shouldFindAllByFilter() {
        // given
        long[] eventIds = eventRepository.findAll().stream().mapToLong(Event::getId).toArray();
        LocalDateTime startDate = LocalDateTime.of(2020, Month.AUGUST, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, Month.OCTOBER, 1, 0, 0, 0);
        long[] tagsIds = {tagId};
        long[] severitiesIds = {severityId};
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by("date_time").descending());

        // when
        Page<Event> events = eventRepository.findByFilter(
                eventIds,
                startDate, endDate,
                tagsIds, severitiesIds,
                pageRequest
        );

        // then
        assertNotNull(events);
        assertEquals(MOCK_EVENTS_NUMBER, events.getTotalElements());
        assertTrue(events.getContent().stream().allMatch(event -> event.getId() != null));
    }

    @Test
    public void shouldFindSomeByFilter() {
        // given
        long[] eventIds = eventRepository.findAll().stream().mapToLong(Event::getId).toArray();
        LocalDateTime startDate = LocalDateTime.of(2020, Month.AUGUST, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, Month.MARCH, 1, 0, 0, 0);
        long[] tagsIds = {tagId};
        long[] severitiesIds = {severityId};
        PageRequest pageRequest = PageRequest.of(0, 20);

        // when
        Page<Event> events = eventRepository.findByFilter(
                eventIds,
                startDate, endDate,
                tagsIds, severitiesIds,
                pageRequest
        );

        // then
        assertNotNull(events);
        assertEquals(1, events.getTotalElements());
        Event event = events.getContent().get(0);
        assertNotNull(event.getId());
    }

    @Test
    public void shouldNotFindByFilter() {
        // given
        long[] eventIds = eventRepository.findAll().stream().mapToLong(Event::getId).toArray();
        LocalDateTime startDate = LocalDateTime.of(2021, Month.NOVEMBER, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, Month.DECEMBER, 1, 0, 0, 0);
        long[] tagsIds = {tagId};
        long[] severitiesIds = {severityId};
        PageRequest pageRequest = PageRequest.of(0, 20);

        // when
        Page<Event> events = eventRepository.findByFilter(
                eventIds,
                startDate, endDate,
                tagsIds, severitiesIds,
                pageRequest
        );

        // then
        assertNotNull(events);
        assertEquals(0, events.getTotalElements());
    }

    @Test
    public void shouldFindByUserIdOrderByDateTimeDesc() {
        // given
        // when
        List<Event> events = eventRepository.findByUserIdOrderByDateTimeDesc(userId);

        // then
        assertNotNull(events);
        assertEquals(MOCK_EVENTS_NUMBER, events.size());
        assertTrue(events.stream().allMatch(event -> event.getId() != null));
    }

    @Test
    public void shouldNotFindByUserIdOrderByDateTimeDesc() {
        // given
        long wrongUserId = userId + 1;

        // when
        List<Event> events = eventRepository.findByUserIdOrderByDateTimeDesc(wrongUserId);

        // then
        assertNotNull(events);
        assertEquals(0, events.size());
    }

}
