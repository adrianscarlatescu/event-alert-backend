package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventSeverityRequestDto;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.service.EventSeverityService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventSeverityController.class)
class EventSeverityControllerTest extends AbstractControllerTest {

    @MockBean
    private EventSeverityService severityService;

    private static final String SEVERITIES_PATH = "/severities";

    private final Long severityId = 1L;
    private final String severityName = "severity";
    private final Integer severityColor = 999;

    @Test
    public void shouldGetAll() throws Exception {
        // given
        EventSeverity severity = getMockSeverity();
        given(severityService.findAll()).willReturn(Collections.singletonList(severity));

        // when
        // then
        mockMvc.perform(get(SEVERITIES_PATH).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(severityId.intValue())))
                .andExpect(jsonPath("$[0].name", is(severityName)))
                .andExpect(jsonPath("$[0].color", is(severityColor)));
    }

    @Test
    public void shouldGetById() throws Exception {
        // given
        EventSeverity severity = getMockSeverity();
        given(severityService.findById(severityId)).willReturn(severity);

        // when
        // then
        mockMvc.perform(get(SEVERITIES_PATH + "/{id}", severityId).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(severityId.intValue())))
                .andExpect(jsonPath("$.name", is(severityName)))
                .andExpect(jsonPath("$.color", is(severityColor)));
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventSeverityRequestDto severityRequestDto = new EventSeverityRequestDto();
        severityRequestDto.setName(severityName);
        severityRequestDto.setColor(severityColor);

        EventSeverity severity = getMockSeverity();
        given(severityService.save(severityRequestDto)).willReturn(severity);

        String jsonBody = objectMapper.writeValueAsString(severity);

        // when
        // then
        mockMvc.perform(post(SEVERITIES_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(severityId.intValue())))
                .andExpect(jsonPath("$.name", is(severityName)))
                .andExpect(jsonPath("$.color", is(severityColor)));
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventSeverityRequestDto severityRequestDto = new EventSeverityRequestDto();
        severityRequestDto.setName(severityName);
        severityRequestDto.setColor(severityColor);

        EventSeverity severity = getMockSeverity();

        given(severityService.updateById(severityRequestDto, severityId)).willReturn(severity);

        String jsonBody = objectMapper.writeValueAsString(severity);

        // when
        // then
        mockMvc.perform(put(SEVERITIES_PATH + "/{id}", severityId).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(severityId.intValue())))
                .andExpect(jsonPath("$.name", is(severityName)))
                .andExpect(jsonPath("$.color", is(severityColor)));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(SEVERITIES_PATH + "/{id}", severityId).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private EventSeverity getMockSeverity() {
        EventSeverity severity = new EventSeverity();
        severity.setId(severityId);
        severity.setName(severityName);
        severity.setColor(severityColor);
        return severity;
    }

}
