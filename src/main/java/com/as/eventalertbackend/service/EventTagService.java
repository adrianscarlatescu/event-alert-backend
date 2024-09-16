package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventTagRequestDto;
import com.as.eventalertbackend.dto.response.EventTagResponseDto;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.EventTag;
import com.as.eventalertbackend.jpa.reopsitory.EventTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventTagService {

    private final EventTagRepository tagRepository;

    @Autowired
    public EventTagService(EventTagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<EventTagResponseDto> findAll() {
        return tagRepository.findAll().stream()
                .map(EventTag::toDto)
                .collect(Collectors.toList());
    }

    public EventTagResponseDto findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.TAG_NOT_FOUND))
                .toDto();
    }

    public EventTagResponseDto updateById(EventTagRequestDto tagRequestDto, Long id) {
        EventTag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.TAG_NOT_FOUND));

        tag.setName(tagRequestDto.getName());
        tag.setImagePath(tagRequestDto.getImagePath());

        return tagRepository.save(tag).toDto();
    }

    public EventTagResponseDto save(EventTagRequestDto tagRequestDto) {
        EventTag tag = new EventTag();

        tag.setName(tagRequestDto.getName());
        tag.setImagePath(tagRequestDto.getImagePath());

        return tagRepository.save(tag).toDto();
    }

    public void deleteById(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.TAG_NOT_FOUND);
        }
    }

}
