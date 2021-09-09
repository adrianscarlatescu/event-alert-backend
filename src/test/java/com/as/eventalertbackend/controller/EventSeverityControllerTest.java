package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.service.EventSeverityService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventSeverityControllerTest extends AbstractControllerTest {

    @MockBean
    private EventSeverityService severityService;

    private static final String SEVERITIES_PATH = "/severities";

    private final Long id = 1L;
    private final String name = "severity";
    private final int color = 999;

    @Test
    public void shouldGetAll() throws Exception {
        // given
        EventSeverity severity = getMockSeverity();
        given(severityService.findAll()).willReturn(Collections.singletonList(severity));

        // when
        // then
        mockMvc.perform(get(SEVERITIES_PATH).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id.intValue())))
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[0].color", is(color)));
    }

    @Test
    public void shouldGetById() throws Exception {
        // given
        EventSeverity severity = getMockSeverity();
        given(severityService.findById(id)).willReturn(severity);

        // when
        // then
        mockMvc.perform(get(SEVERITIES_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.color", is(color)));
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventSeverity severity = getMockSeverity();
        given(severityService.save(any())).willReturn(severity);

        String jsonBody = objectMapper.writeValueAsString(severity);

        // when
        // then
        mockMvc.perform(post(SEVERITIES_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.color", is(color)));
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventSeverity severity = getMockSeverity();
        given(severityService.updateById(any(), any())).willReturn(severity);

        String jsonBody = objectMapper.writeValueAsString(severity);

        // when
        // then
        mockMvc.perform(put(SEVERITIES_PATH + "/{id}", id).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.color", is(color)));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(SEVERITIES_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private EventSeverity getMockSeverity() {
        EventSeverity severity = new EventSeverity();
        severity.setId(id);
        severity.setName(name);
        severity.setColor(color);
        return severity;
    }

}
