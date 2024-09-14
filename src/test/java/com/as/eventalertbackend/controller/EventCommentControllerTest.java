package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventCommentRequestDto;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.service.EventCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventCommentController.class)
class EventCommentControllerTest extends AbstractControllerTest {

    @MockBean
    private EventCommentService commentService;

    private static final String COMMENTS_PATH = "/comments";

    private final Long commentId = 1L;
    private final String commentMessage = "test";

    private final Long userId = 1L;
    private final Long eventId = 1L;

    @Test
    public void shouldGetByEventId() throws Exception {
        // given
        EventComment comment = getMockComment();
        given(commentService.findAllByEventId(eventId)).willReturn(Collections.singletonList(comment));

        // when
        // then
        mockMvc.perform(get(COMMENTS_PATH + "/{id}", eventId).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(commentId.intValue())))
                .andExpect(jsonPath("$[0].comment", is(commentMessage)))
                .andExpect(jsonPath("$[0].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[0].user.id", is(userId.intValue())));
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventCommentRequestDto commentRequestDto = new EventCommentRequestDto();
        commentRequestDto.setComment(commentMessage);
        commentRequestDto.setEventId(eventId);
        commentRequestDto.setUserId(userId);

        EventComment comment = getMockComment();

        given(commentService.save(commentRequestDto)).willReturn(comment);

        String jsonBody = objectMapper.writeValueAsString(commentRequestDto);

        // when
        // then
        mockMvc.perform(post(COMMENTS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(commentId.intValue())))
                .andExpect(jsonPath("$.comment", is(commentMessage)))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.user.id", is(userId.intValue())));
    }

    @Test
    public void shouldNotSave() throws Exception {
        // given
        EventCommentRequestDto commentRequestDto = new EventCommentRequestDto();
        commentRequestDto.setEventId(eventId);
        commentRequestDto.setUserId(userId);

        String jsonBody = objectMapper.writeValueAsString(commentRequestDto);

        // when
        // then
        mockMvc.perform(post(COMMENTS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("The comment is mandatory")));
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventCommentRequestDto commentRequestDto = new EventCommentRequestDto();
        commentRequestDto.setComment(commentMessage);
        commentRequestDto.setEventId(eventId);
        commentRequestDto.setUserId(userId);

        EventComment comment = getMockComment();

        given(commentService.updateById(commentRequestDto, eventId)).willReturn(comment);

        String jsonBody = objectMapper.writeValueAsString(commentRequestDto);

        // when
        // then
        mockMvc.perform(put(COMMENTS_PATH + "/{id}", eventId).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(COMMENTS_PATH + "/{id}", commentId).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private EventComment getMockComment() {
        User user = new User();
        user.setId(userId);

        EventComment comment = new EventComment();
        comment.setId(commentId);
        comment.setComment(commentMessage);
        comment.setDateTime(LocalDateTime.now());
        comment.setUser(user);

        return comment;
    }

}
