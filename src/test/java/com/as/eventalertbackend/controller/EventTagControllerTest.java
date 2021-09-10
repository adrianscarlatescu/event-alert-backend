package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.service.EventTagService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventTagController.class)
class EventTagControllerTest extends AbstractControllerTest {

    @MockBean
    private EventTagService tagService;

    private static final String TAGS_PATH = "/tags";

    private final Long id = 1L;
    private final String name = "tag";
    private final String imagePath = "img/tag_1.png";

    @Test
    public void shouldGetAll() throws Exception {
        // given
        EventTag tag = getMockTag();
        given(tagService.findAll()).willReturn(Collections.singletonList(tag));

        // when
        // then
        mockMvc.perform(get(TAGS_PATH).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id.intValue())))
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[0].imagePath", is(imagePath)));
    }

    @Test
    public void shouldGetById() throws Exception {
        // given
        EventTag tag = getMockTag();
        given(tagService.findById(id)).willReturn(tag);

        // when
        // then
        mockMvc.perform(get(TAGS_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.imagePath", is(imagePath)));
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventTag tag = getMockTag();
        given(tagService.save(any())).willReturn(tag);

        String jsonBody = objectMapper.writeValueAsString(tag);

        // when
        // then
        mockMvc.perform(post(TAGS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.imagePath", is(imagePath)));
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventTag tag = getMockTag();
        given(tagService.updateById(any(), any())).willReturn(tag);

        String jsonBody = objectMapper.writeValueAsString(tag);

        // when
        // then
        mockMvc.perform(put(TAGS_PATH + "/{id}", id).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.imagePath", is(imagePath)));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(TAGS_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private EventTag getMockTag() {
        EventTag tag = new EventTag();
        tag.setId(id);
        tag.setName(name);
        tag.setImagePath(imagePath);
        return tag;
    }

}
