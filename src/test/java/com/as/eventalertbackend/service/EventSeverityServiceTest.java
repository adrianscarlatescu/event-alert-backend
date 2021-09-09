package com.as.eventalertbackend.service;

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
    private EventSeverityRepository repository;

    @InjectMocks
    private EventSeverityService service;

    @Test
    public void shouldFindById() {
        // given
        Long id = 1L;
        EventSeverity mockSeverity = new EventSeverity();
        mockSeverity.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(mockSeverity));

        // when
        EventSeverity severity = service.findById(id);

        // then
        assertNotNull(severity);
        assertEquals(id, severity.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(repository.findById(any())).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> service.findById(any()));
    }

    @Test
    public void shouldSave() {
        // given
        Long id = 1L;
        String name = "name";
        int color = 99999;

        EventSeverity mockSeverity = new EventSeverity();
        mockSeverity.setId(id);
        mockSeverity.setName(name);
        mockSeverity.setColor(color);

        given(repository.save(mockSeverity)).willReturn(mockSeverity);

        // when
        EventSeverity severity = service.save(mockSeverity);

        // then
        ArgumentCaptor<EventSeverity> argumentCaptor = ArgumentCaptor.forClass(EventSeverity.class);
        verify(repository).save(argumentCaptor.capture());

        EventSeverity capturedEventSeverity = argumentCaptor.getValue();

        assertEquals(mockSeverity, capturedEventSeverity);
        assertNotNull(severity);
        assertEquals(id, severity.getId());
        assertEquals(name, severity.getName());
        assertEquals(color, severity.getColor());
    }

    @Test
    public void shouldUpdateById() {
        // given
        Long id = 1L;
        String updatedName = "updatedName";
        int updatedColor = 99999;

        EventSeverity mockSeverity = new EventSeverity();
        mockSeverity.setId(id);
        mockSeverity.setName(updatedName);
        mockSeverity.setColor(updatedColor);

        EventSeverity mockDbObjSeverity = new EventSeverity();
        mockDbObjSeverity.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(mockDbObjSeverity));
        given(repository.save(mockDbObjSeverity)).willReturn(mockDbObjSeverity);

        // when
        EventSeverity severity = service.updateById(mockSeverity, id);

        // then
        assertNotNull(severity);
        assertEquals(updatedName, severity.getName());
        assertEquals(updatedColor, severity.getColor());
    }

    @Test
    public void shouldDeleteById() {
        // given
        Long id = 1L;
        given(repository.existsById(id)).willReturn(true);

        // when
        service.deleteById(id);

        // then
        verify(repository).deleteById(id);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        Long id = 1L;
        given(repository.existsById(id)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> service.deleteById(id));
        verify(repository, times(0)).deleteById(id);
    }

}
