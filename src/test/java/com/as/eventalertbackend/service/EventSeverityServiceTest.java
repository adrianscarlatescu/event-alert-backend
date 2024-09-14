package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventSeverityRequestDto;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.reopsitory.EventSeverityRepository;
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
class EventSeverityServiceTest {

    @Mock
    private EventSeverityRepository severityRepository;

    @InjectMocks
    private EventSeverityService severityService;

    private final Long severityId = 1L;
    private final String severityName = "severity";
    private final Integer severityColor = 999;

    @Test
    public void shouldFindById() {
        // given
        EventSeverity mockSeverity = new EventSeverity();
        mockSeverity.setId(severityId);

        given(severityRepository.findById(severityId)).willReturn(Optional.of(mockSeverity));

        // when
        EventSeverity severity = severityService.findById(severityId);

        // then
        assertNotNull(severity);
        assertEquals(severityId, severity.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(severityRepository.findById(severityId)).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> severityService.findById(severityId));
    }

    @Test
    public void shouldSave() {
        // given
        EventSeverityRequestDto severityRequestDto = new EventSeverityRequestDto();
        severityRequestDto.setName(severityName);
        severityRequestDto.setColor(severityColor);

        EventSeverity mockSeverity = new EventSeverity();
        mockSeverity.setId(severityId);
        mockSeverity.setName(severityName);
        mockSeverity.setColor(severityColor);

        given(severityRepository.save(any())).willReturn(mockSeverity);

        // when
        EventSeverity severity = severityService.save(severityRequestDto);

        // then
        ArgumentCaptor<EventSeverity> argumentCaptor = ArgumentCaptor.forClass(EventSeverity.class);
        verify(severityRepository).save(argumentCaptor.capture());

        EventSeverity capturedSeverity = argumentCaptor.getValue();

        assertEquals(mockSeverity.getName(), capturedSeverity.getName());
        assertEquals(mockSeverity.getColor().intValue(), capturedSeverity.getColor().intValue());
        assertNotNull(severity);
    }

    @Test
    public void shouldUpdateById() {
        // given
        EventSeverityRequestDto severityRequestDto = new EventSeverityRequestDto();
        severityRequestDto.setName(severityName);
        severityRequestDto.setColor(severityColor);

        EventSeverity eventSeverity = new EventSeverity();
        eventSeverity.setId(severityId);

        given(severityRepository.findById(severityId)).willReturn(Optional.of(eventSeverity));
        given(severityRepository.save(eventSeverity)).willReturn(eventSeverity);

        // when
        EventSeverity severity = severityService.updateById(severityRequestDto, severityId);

        // then
        assertNotNull(severity);
        assertEquals(severityName, severity.getName());
        assertEquals(severityColor, severity.getColor());
    }

    @Test
    public void shouldDeleteById() {
        // given
        given(severityRepository.existsById(severityId)).willReturn(true);

        // when
        severityService.deleteById(severityId);

        // then
        verify(severityRepository).deleteById(severityId);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        given(severityRepository.existsById(severityId)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> severityService.deleteById(severityId));
        verify(severityRepository, times(0)).deleteById(severityId);
    }

}
