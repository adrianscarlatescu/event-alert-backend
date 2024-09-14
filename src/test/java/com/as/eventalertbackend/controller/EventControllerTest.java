package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventFilterRequestDto;
import com.as.eventalertbackend.controller.request.EventRequestDto;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest extends AbstractControllerTest {

    @MockBean
    private EventService eventService;

    private static final String EVENTS_PATH = "/events";

    private final Long eventId = 1L;
    private final Double eventLatitude = 44.4555611;
    private final Double eventLongitude = 26.0404115;
    private final String eventImagePath = "img/event_1.png";
    private final String eventDescription = "description";
    private final LocalDateTime eventDateTime = LocalDateTime.of(2020, Month.JUNE, 1, 14, 25, 45);

    private final Long severityId = 1L;
    private final Long tagId = 1L;
    private final Long userId = 1L;

    private final ObjectMapper jackson2ObjectMapper = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();

    @Test
    public void shouldGetById() throws Exception {
        // given
        Event event = getMockEvent();
        given(eventService.findById(eventId)).willReturn(event);

        // when
        // then
        mockMvc.perform(get(EVENTS_PATH + "/{id}", eventId).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(eventId.intValue())))
                .andExpect(jsonPath("$.latitude", is(eventLatitude)))
                .andExpect(jsonPath("$.longitude", is(eventLongitude)))
                .andExpect(jsonPath("$.imagePath", is(eventImagePath)))
                .andExpect(jsonPath("$.description", is(eventDescription)))
                .andExpect(jsonPath("$.dateTime", is(eventDateTime.toString())));
    }

    @Test
    public void shouldGetByFilter() throws Exception {
        // given
        EventFilterRequestDto filterRequestDto = new EventFilterRequestDto();
        filterRequestDto.setRadius(100);
        filterRequestDto.setStartDate(LocalDate.of(2020, Month.APRIL, 1));
        filterRequestDto.setEndDate(LocalDate.of(2020, Month.SEPTEMBER, 1));
        filterRequestDto.setLatitude(0.0);
        filterRequestDto.setLongitude(0.0);
        filterRequestDto.setTagsIds(new long[]{1});
        filterRequestDto.setSeveritiesIds(new long[]{1});
        String jsonBody = jackson2ObjectMapper.writeValueAsString(filterRequestDto);

        Event event = getMockEvent();
        Page<Event> pagedResponse = new PageImpl<>(Collections.singletonList(event), Pageable.unpaged(), 1);

        given(eventService.findByFilter(filterRequestDto, 20, 1, null)).willReturn(pagedResponse);

        // when
        // then
        mockMvc.perform(post(EVENTS_PATH + "/filter" + "?pageSize=20&pageNumber=1").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].id", is(eventId.intValue())));
    }

    @Test
    public void shouldSave() throws Exception {
        // given
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setLatitude(eventLatitude);
        eventRequestDto.setLongitude(eventLongitude);
        eventRequestDto.setImagePath(eventImagePath);
        eventRequestDto.setSeverityId(severityId);
        eventRequestDto.setTagId(tagId);
        eventRequestDto.setUserId(userId);
        eventRequestDto.setDescription(eventDescription);

        Event event = getMockEvent();

        given(eventService.save(eventRequestDto)).willReturn(event);

        String jsonBody = objectMapper.writeValueAsString(eventRequestDto);

        // when
        // then
        mockMvc.perform(post(EVENTS_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(eventId.intValue())))
                .andExpect(jsonPath("$.latitude", is(eventLatitude)))
                .andExpect(jsonPath("$.longitude", is(eventLongitude)))
                .andExpect(jsonPath("$.dateTime").isNotEmpty());
    }

    @Test
    public void shouldUpdateById() throws Exception {
        // given
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setLatitude(eventLatitude);
        eventRequestDto.setLongitude(eventLongitude);
        eventRequestDto.setImagePath(eventImagePath);
        eventRequestDto.setSeverityId(severityId);
        eventRequestDto.setTagId(tagId);
        eventRequestDto.setUserId(userId);
        eventRequestDto.setDescription(eventDescription);

        Event event = getMockEvent();

        given(eventService.updateById(eventRequestDto, eventId)).willReturn(event);

        String jsonBody = objectMapper.writeValueAsString(eventRequestDto);

        // when
        // then
        mockMvc.perform(put(EVENTS_PATH + "/{id}", eventId).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(EVENTS_PATH + "/{id}", eventId).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private Event getMockEvent() {
        EventSeverity severity = new EventSeverity();
        severity.setId(severityId);

        EventTag tag = new EventTag();
        tag.setId(tagId);

        User user = new User();
        user.setId(userId);

        Event event = new Event();
        event.setId(eventId);
        event.setLatitude(eventLatitude);
        event.setLongitude(eventLongitude);
        event.setImagePath(eventImagePath);
        event.setDescription(eventDescription);
        event.setDateTime(eventDateTime);

        event.setSeverity(severity);
        event.setTag(tag);
        event.setUser(user);

        return event;
    }

}
