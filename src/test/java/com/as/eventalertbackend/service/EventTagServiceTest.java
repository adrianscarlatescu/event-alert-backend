package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.data.reopsitory.EventTagRepository;
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
class EventTagServiceTest {

    @Mock
    private EventTagRepository repository;

    @InjectMocks
    private EventTagService service;

    @Test
    public void shouldFindById() {
        // given
        Long id = 1L;
        EventTag mockTag = new EventTag();
        mockTag.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(mockTag));

        // when
        EventTag tag = service.findById(id);

        // then
        assertNotNull(tag);
        assertEquals(id, tag.getId());
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
        String imagePath = "imagePath";

        EventTag mockTag = new EventTag();
        mockTag.setId(id);
        mockTag.setName(name);
        mockTag.setImagePath(imagePath);

        given(repository.save(mockTag)).willReturn(mockTag);

        // when
        EventTag tag = service.save(mockTag);

        // then
        ArgumentCaptor<EventTag> argumentCaptor = ArgumentCaptor.forClass(EventTag.class);
        verify(repository).save(argumentCaptor.capture());

        EventTag capturedEventTag = argumentCaptor.getValue();

        assertEquals(mockTag, capturedEventTag);
        assertNotNull(tag);
        assertEquals(id, tag.getId());
        assertEquals(name, tag.getName());
        assertEquals(imagePath, tag.getImagePath());
    }

    @Test
    public void shouldUpdateById() {
        // given
        Long id = 1L;
        String updatedName = "updatedName";
        String updatedImagePath = "updatedImagePath";

        EventTag mockTag = new EventTag();
        mockTag.setId(id);
        mockTag.setName(updatedName);
        mockTag.setImagePath(updatedImagePath);

        EventTag mockDbObjTag = new EventTag();
        mockDbObjTag.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(mockDbObjTag));
        given(repository.save(mockDbObjTag)).willReturn(mockDbObjTag);

        // when
        EventTag tag = service.updateById(mockTag, id);

        // then
        assertNotNull(tag);
        assertEquals(updatedName, tag.getName());
        assertEquals(updatedImagePath, tag.getImagePath());
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
