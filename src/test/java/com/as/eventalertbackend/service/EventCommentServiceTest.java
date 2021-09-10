package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventCommentBody;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.reopsitory.EventCommentRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Test
    public void shouldFindById() {
        // given
        Long id = 1L;
        EventComment mockComment = new EventComment();
        mockComment.setId(id);

        given(commentRepository.findById(id)).willReturn(Optional.of(mockComment));

        // when
        EventComment comment = commentService.findById(id);

        // then
        assertNotNull(comment);
        assertEquals(id, comment.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(commentRepository.findById(any())).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> commentService.findById(any()));
    }

    @Test
    public void shouldSave() {
        // given
        Long id = 1L;
        String title = "test";

        EventCommentBody body = new EventCommentBody();
        body.setEventId(1L);
        body.setUserId(1L);
        body.setComment(title);

        EventComment mockComment = new EventComment();
        mockComment.setId(id);
        mockComment.setComment(title);

        given(eventService.findById(1L)).willReturn(new Event());
        given(userService.findById(1L)).willReturn(new User());
        given(commentRepository.save(any())).willReturn(mockComment);

        // when
        EventComment comment = commentService.save(body);

        // then
        verify(commentRepository).save(any());
        assertNotNull(comment);
        assertEquals(id, comment.getId());
        assertEquals(title, comment.getComment());
    }

    @Test
    public void shouldUpdateById() {
        // given
        Long id = 1L;
        String updatedTitle = "test";

        EventCommentBody body = new EventCommentBody();
        body.setEventId(1L);
        body.setUserId(1L);
        body.setComment(updatedTitle);

        EventComment mockDbObjComment = new EventComment();
        mockDbObjComment.setId(id);

        given(eventService.findById(1L)).willReturn(new Event());
        given(userService.findById(1L)).willReturn(new User());
        given(commentRepository.findById(id)).willReturn(Optional.of(mockDbObjComment));
        given(commentRepository.save(mockDbObjComment)).willReturn(mockDbObjComment);

        // when
        EventComment comment = commentService.updateById(body, id);

        // then
        assertNotNull(comment);
        assertEquals(updatedTitle, comment.getComment());
    }

    @Test
    public void shouldDeleteById() {
        // given
        Long id = 1L;
        given(commentRepository.existsById(id)).willReturn(true);

        // when
        commentService.deleteById(id);

        // then
        verify(commentRepository).deleteById(id);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        Long id = 1L;
        given(commentRepository.existsById(id)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> commentService.deleteById(id));
        verify(commentRepository, times(0)).deleteById(id);
    }

}
