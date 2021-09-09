package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventCommentRepositoryTest {

    @Autowired
    private EventCommentRepository commentRepository;
    @Autowired
    private EventSeverityRepository severityRepository;
    @Autowired
    private EventTagRepository tagRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    private static final int MOCK_COMMENTS_NUMBER = 2;

    private Long eventId;

    @BeforeEach
    void setUp() {
        User user = new User();
        userRepository.save(user);

        EventSeverity severity = new EventSeverity();
        severity.setName("test");
        severityRepository.save(severity);

        EventTag tag = new EventTag();
        tag.setName("test");
        tag.setImagePath("img/tag_1.png");
        tagRepository.save(tag);

        Event event = new Event();
        event.setSeverity(severity);
        event.setTag(tag);
        event.setUser(user);
        Event savedEvent = eventRepository.save(event);
        eventId = savedEvent.getId();

        for (int i = 0; i < MOCK_COMMENTS_NUMBER; i++) {
            EventComment comment = new EventComment();
            comment.setUser(user);
            comment.setEvent(event);
            EventComment savedComment = commentRepository.save(comment);
            // Update creation timestamp
            savedComment.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 1 + i, 10, 30, 0));
            commentRepository.save(savedComment);
        }
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();
        severityRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    public void shouldFindByEventIdOrderByDateTimeDesc() {
        // given
        // when
        List<EventComment> comments = commentRepository.findByEventIdOrderByDateTimeDesc(eventId);

        // then
        assertNotNull(comments);
        assertEquals(MOCK_COMMENTS_NUMBER, comments.size());
        assertTrue(comments.get(0).getDateTime().isAfter(comments.get(1).getDateTime()));
    }

    @Test
    public void shouldNotFindByEventIdOrderByDateTimeDesc() {
        // given
        // when
        List<EventComment> comments = commentRepository.findByEventIdOrderByDateTimeDesc(eventId + 1);

        // then
        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }

}
