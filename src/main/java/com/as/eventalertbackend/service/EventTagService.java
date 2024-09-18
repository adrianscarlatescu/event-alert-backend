package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventTagRequestDto;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.EventTag;
import com.as.eventalertbackend.jpa.reopsitory.EventTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTagService {

    private final EventTagRepository tagRepository;

    @Autowired
    public EventTagService(EventTagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<EventTag> findAll() {
        return tagRepository.findAll();
    }

    public EventTag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.TAG_NOT_FOUND));
    }

    public EventTag updateById(EventTagRequestDto tagRequestDto, Long id) {
        EventTag tag = findById(id);

        tag.setName(tagRequestDto.getName());
        tag.setImagePath(tagRequestDto.getImagePath());

        return tagRepository.save(tag);
    }

    public EventTag save(EventTagRequestDto tagRequestDto) {
        EventTag tag = new EventTag();

        tag.setName(tagRequestDto.getName());
        tag.setImagePath(tagRequestDto.getImagePath());

        return tagRepository.save(tag);
    }

    public void deleteById(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.TAG_NOT_FOUND);
        }
    }

}
