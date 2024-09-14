package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventCommentRequestDto;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.reopsitory.EventCommentRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventCommentServiceTest {

    @Mock
    private EventCommentRepository commentRepository;
    @Mock
    private EventService eventService;
    @Mock
    private UserService userService;

    @InjectMocks
    private EventCommentService commentService;

    private final Long commentId = 1L;
    private final String commentMessage = "test";

    private final Long eventId = 1L;
    private final Long userId = 1L;

    @Test
    public void shouldFindById() {
        // given
        EventComment mockComment = new EventComment();
        mockComment.setId(commentId);
        mockComment.setComment(commentMessage);

        given(commentRepository.findById(commentId)).willReturn(Optional.of(mockComment));

        // when
        EventComment comment = commentService.findById(commentId);

        // then
        assertNotNull(comment);
        assertEquals(commentId, comment.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(commentRepository.findById(commentId)).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> commentService.findById(commentId));
    }

    @Test
    public void shouldSave() {
        // given
        EventCommentRequestDto commentRequestDto = new EventCommentRequestDto();
        commentRequestDto.setEventId(eventId);
        commentRequestDto.setUserId(userId);
        commentRequestDto.setComment(commentMessage);

        Event mockEvent = new Event();
        mockEvent.setId(eventId);

        User mockUser = new User();
        mockUser.setId(userId);

        EventComment mockComment = new EventComment();
        mockComment.setId(commentId);
        mockComment.setComment(commentMessage);
        mockComment.setEvent(mockEvent);
        mockComment.setUser(mockUser);

        given(eventService.findById(eventId)).willReturn(mockEvent);
        given(userService.findById(userId)).willReturn(mockUser);
        given(commentRepository.save(any())).willReturn(mockComment);

        // when
        EventComment comment = commentService.save(commentRequestDto);

        // then
        ArgumentCaptor<EventComment> argumentCaptor = ArgumentCaptor.forClass(EventComment.class);
        verify(commentRepository).save(argumentCaptor.capture());

        EventComment capturedComment = argumentCaptor.getValue();

        assertEquals(mockComment.getComment(), capturedComment.getComment());
        assertEquals(mockComment.getEvent().getId().longValue(), capturedComment.getEvent().getId().longValue());
        assertEquals(mockComment.getUser().getId().longValue(), capturedComment.getUser().getId().longValue());
    }

    @Test
    public void shouldUpdateById() {
        // given
        EventCommentRequestDto commentRequestDto = new EventCommentRequestDto();
        commentRequestDto.setEventId(eventId);
        commentRequestDto.setUserId(userId);
        commentRequestDto.setComment(commentMessage);

        Event mockEvent = new Event();
        mockEvent.setId(eventId);

        User mockUser = new User();
        mockUser.setId(userId);

        EventComment mockComment = new EventComment();
        mockComment.setId(commentId);
        mockComment.setComment(commentMessage);
        mockComment.setEvent(mockEvent);
        mockComment.setUser(mockUser);

        given(eventService.findById(eventId)).willReturn(new Event());
        given(userService.findById(userId)).willReturn(new User());
        given(commentRepository.findById(commentId)).willReturn(Optional.of(mockComment));
        given(commentRepository.save(mockComment)).willReturn(mockComment);

        // when
        EventComment comment = commentService.updateById(commentRequestDto, commentId);

        // then
        assertNotNull(comment);
        assertEquals(commentMessage, comment.getComment());
    }

    @Test
    public void shouldDeleteById() {
        // given
        given(commentRepository.existsById(commentId)).willReturn(true);

        // when
        commentService.deleteById(commentId);

        // then
        verify(commentRepository).deleteById(commentId);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        given(commentRepository.existsById(commentId)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> commentService.deleteById(commentId));
        verify(commentRepository, times(0)).deleteById(commentId);
    }

}
