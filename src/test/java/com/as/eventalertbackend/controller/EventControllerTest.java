package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventBody;
import com.as.eventalertbackend.controller.request.EventFilterBody;
import com.as.eventalertbackend.controller.response.PagedResponse;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest extends AbstractControllerTest {

    @MockBean
    private EventService eventService;

    private static final String EVENTS_PATH = "/events";

    private final Long id = 1L;
    private final Double latitude = 44.4555611;
    private final Double longitude = 26.0404115;
    private final String imagePath = "img/event_1.png";
    private final String description = "description";
    private final LocalDateTime dateTime = LocalDateTime.of(2020, Month.JUNE, 1, 14, 25, 45);

    private ObjectMapper jackson2ObjectMapper = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();

    @Test
    public void shouldGetById() throws Exception {
        // given
        Event event = getMockEvent();
        given(eventService.findById(id)).willReturn(event);

        // when
        // then
        mockMvc.perform(get(EVENTS_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.latitude", is(latitude)))
                .andExpect(jsonPath("$.longitude", is(longitude)))
                .andExpect(jsonPath("$.imagePath", is(imagePath)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.dateTime", is(dateTime.toString())));
    }

    @Test
    public void shouldGetByFilter() throws Exception {
        // given
        EventFilterBody body = new EventFilterBody();
        body.setRadius(100);
        body.setStartDate(LocalDate.of(2020, Month.APRIL, 1));
        body.setEndDate(LocalDate.of(2020, Month.SEPTEMBER, 1));
        body.setLatitude(0.0);
        body.setLongitude(0.0);
        body.setTagsIds(new long[]{1});
        body.setSeveritiesIds(new long[]{1});
        String jsonBody = jackson2ObjectMapper.writeValueAsString(body);

        Event event = getMockEvent();
        PagedResponse<Event> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(Collections.singletonList(event));
        pagedResponse.setTotalPages(1);
        pagedResponse.setTotalElements(1);

        given(eventService.findByFilter(any(), anyInt(), anyInt(), any())).willReturn(pagedResponse);

        // when
        // then
        mockMvc.perform(post(EVENTS_PATH + "/filter" + "?pageSize=20&pageNumber=1").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].id", is(id.intValue())));
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventBody body = new EventBody();
        body.setLatitude(latitude);
        body.setLongitude(longitude);
        body.setImagePath(imagePath);
        body.setSeverityId(1L);
        body.setTagId(1L);
        body.setUserId(1L);
        body.setDescription(description);

        Event event = getMockEvent();

        given(eventService.save(any(EventBody.class))).willReturn(event);

        String jsonBody = objectMapper.writeValueAsString(body);

        // when
        // then
        mockMvc.perform(post(EVENTS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.latitude", is(latitude)))
                .andExpect(jsonPath("$.longitude", is(longitude)))
                .andExpect(jsonPath("$.dateTime").isNotEmpty());
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventBody body = new EventBody();
        body.setLatitude(latitude);
        body.setLongitude(longitude);
        body.setImagePath(imagePath);
        body.setSeverityId(1L);
        body.setTagId(1L);
        body.setUserId(1L);
        body.setDescription(description);

        Event event = getMockEvent();

        given(eventService.updateById(any(), any())).willReturn(event);

        String jsonBody = objectMapper.writeValueAsString(body);

        // when
        // then
        mockMvc.perform(put(EVENTS_PATH + "/{id}", id).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(EVENTS_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private Event getMockEvent() {
        Event event = new Event();
        event.setId(id);
        event.setLatitude(latitude);
        event.setLongitude(longitude);
        event.setImagePath(imagePath);
        event.setDescription(description);
        event.setDateTime(dateTime);
        return event;
    }

}
