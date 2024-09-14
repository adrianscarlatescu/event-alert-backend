package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventTagRequestDto;
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
    private EventTagRepository tagRepository;

    @InjectMocks
    private EventTagService tagService;

    private final Long tagId = 1L;
    private final String tagName = "name";
    private final String tagImagePath = "img/tag_1.png";

    @Test
    public void shouldFindById() {
        // given
        EventTag mockTag = new EventTag();
        mockTag.setId(tagId);

        given(tagRepository.findById(tagId)).willReturn(Optional.of(mockTag));

        // when
        EventTag tag = tagService.findById(tagId);

        // then
        assertNotNull(tag);
        assertEquals(tagId, tag.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(tagRepository.findById(tagId)).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> tagService.findById(tagId));
    }

    @Test
    public void shouldSave() {
        // given
        EventTagRequestDto tagRequestDto = new EventTagRequestDto();
        tagRequestDto.setName(tagName);
        tagRequestDto.setImagePath(tagImagePath);

        EventTag mockTag = new EventTag();
        mockTag.setId(tagId);
        mockTag.setName(tagName);
        mockTag.setImagePath(tagImagePath);

        given(tagRepository.save(any())).willReturn(mockTag);

        // when
        EventTag tag = tagService.save(tagRequestDto);

        // then
        ArgumentCaptor<EventTag> argumentCaptor = ArgumentCaptor.forClass(EventTag.class);
        verify(tagRepository).save(argumentCaptor.capture());

        EventTag capturedTag = argumentCaptor.getValue();

        assertEquals(mockTag.getName(), capturedTag.getName());
        assertEquals(mockTag.getImagePath(), capturedTag.getImagePath());
        assertNotNull(tag);
    }

    @Test
    public void shouldUpdateById() {
        // given
        EventTagRequestDto tagRequestDto = new EventTagRequestDto();
        tagRequestDto.setName(tagName);
        tagRequestDto.setImagePath(tagImagePath);

        EventTag eventTag = new EventTag();
        eventTag.setId(tagId);

        given(tagRepository.findById(tagId)).willReturn(Optional.of(eventTag));
        given(tagRepository.save(eventTag)).willReturn(eventTag);

        // when
        EventTag tag = tagService.updateById(tagRequestDto, tagId);

        // then
        assertNotNull(tag);
        assertEquals(tagName, tag.getName());
        assertEquals(tagImagePath, tag.getImagePath());
    }

    @Test
    public void shouldDeleteById() {
        // given
        given(tagRepository.existsById(tagId)).willReturn(true);

        // when
        tagService.deleteById(tagId);

        // then
        verify(tagRepository).deleteById(tagId);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        given(tagRepository.existsById(tagId)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> tagService.deleteById(tagId));
        verify(tagRepository, times(0)).deleteById(tagId);
    }

}
