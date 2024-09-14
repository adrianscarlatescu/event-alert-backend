package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventTagRequestDto;
import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.service.EventTagService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventTagController.class)
class EventTagControllerTest extends AbstractControllerTest {

    @MockBean
    private EventTagService tagService;

    private static final String TAGS_PATH = "/tags";

    private final Long tagId = 1L;
    private final String tagName = "tag";
    private final String tagImagePath = "img/tag_1.png";

    @Test
    public void shouldGetAll() throws Exception {
        // given
        EventTag tag = getMockTag();
        given(tagService.findAll()).willReturn(Collections.singletonList(tag));

        // when
        // then
        mockMvc.perform(get(TAGS_PATH).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(tagId.intValue())))
                .andExpect(jsonPath("$[0].name", is(tagName)))
                .andExpect(jsonPath("$[0].imagePath", is(tagImagePath)));
    }

    @Test
    public void shouldGetById() throws Exception {
        // given
        EventTag tag = getMockTag();
        given(tagService.findById(tagId)).willReturn(tag);

        // when
        // then
        mockMvc.perform(get(TAGS_PATH + "/{id}", tagId).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tagId.intValue())))
                .andExpect(jsonPath("$.name", is(tagName)))
                .andExpect(jsonPath("$.imagePath", is(tagImagePath)));
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventTagRequestDto tagRequestDto = new EventTagRequestDto();
        tagRequestDto.setName(tagName);
        tagRequestDto.setImagePath(tagImagePath);

        EventTag tag = getMockTag();

        given(tagService.save(tagRequestDto)).willReturn(tag);

        String jsonBody = objectMapper.writeValueAsString(tag);

        // when
        // then
        mockMvc.perform(post(TAGS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(tagId.intValue())))
                .andExpect(jsonPath("$.name", is(tagName)))
                .andExpect(jsonPath("$.imagePath", is(tagImagePath)));
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventTagRequestDto tagRequestDto = new EventTagRequestDto();
        tagRequestDto.setName(tagName);
        tagRequestDto.setImagePath(tagImagePath);

        EventTag tag = getMockTag();

        given(tagService.updateById(tagRequestDto, tagId)).willReturn(tag);

        String jsonBody = objectMapper.writeValueAsString(tag);

        // when
        // then
        mockMvc.perform(put(TAGS_PATH + "/{id}", tagId).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tagId.intValue())))
                .andExpect(jsonPath("$.name", is(tagName)))
                .andExpect(jsonPath("$.imagePath", is(tagImagePath)));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(TAGS_PATH + "/{id}", tagId).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private EventTag getMockTag() {
        EventTag tag = new EventTag();
        tag.setId(tagId);
        tag.setName(tagName);
        tag.setImagePath(tagImagePath);
        return tag;
    }

}
