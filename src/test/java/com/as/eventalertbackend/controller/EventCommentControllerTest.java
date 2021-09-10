package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventCommentBody;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.service.EventCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventCommentController.class)
class EventCommentControllerTest extends AbstractControllerTest {

    @MockBean
    private EventCommentService commentService;

    private static final String COMMENTS_PATH = "/comments";

    private final String message = "test";
    private final Long id = 1L;

    @Test
    public void shouldGetByEventId() throws Exception {
        // given
        Long eventId = 1L;
        EventComment comment = getMockComment();
        given(commentService.findByEventId(eventId)).willReturn(Collections.singletonList(comment));

        // when
        // then
        mockMvc.perform(get(COMMENTS_PATH + "/{id}", eventId).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id.intValue())))
                .andExpect(jsonPath("$[0].comment", is(message)))
                .andExpect(jsonPath("$[0].dateTime").isNotEmpty());
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventCommentBody body = new EventCommentBody();
        body.setComment(message);
        body.setEventId(1L);
        body.setUserId(1L);

        EventComment comment = getMockComment();

        given(commentService.save(any())).willReturn(comment);

        String jsonBody = objectMapper.writeValueAsString(body);

        // when
        // then
        mockMvc.perform(post(COMMENTS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.comment", is(message)))
                .andExpect(jsonPath("$.dateTime").isNotEmpty());
    }

    @Test
    public void shouldNotSave() throws Exception {
        // given
        EventCommentBody body = new EventCommentBody();

        String jsonBody = objectMapper.writeValueAsString(body);

        // when
        // then
        mockMvc.perform(post(COMMENTS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("The comment is mandatory")));
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventCommentBody body = new EventCommentBody();
        body.setComment(message);
        body.setEventId(1L);
        body.setUserId(1L);

        EventComment comment = getMockComment();

        given(commentService.updateById(any(), any())).willReturn(comment);

        String jsonBody = objectMapper.writeValueAsString(body);

        // when
        // then
        mockMvc.perform(put(COMMENTS_PATH + "/{id}", id).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(COMMENTS_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private EventComment getMockComment() {
        EventComment comment = new EventComment();
        comment.setId(id);
        comment.setComment(message);
        comment.setDateTime(LocalDateTime.now());
        return comment;
    }

}
