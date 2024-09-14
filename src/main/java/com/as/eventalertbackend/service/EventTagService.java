package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventTagRequestDto;
import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.data.reopsitory.EventTagRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
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
                .orElseThrow(() -> new RecordNotFoundException("Tag not found"));
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
            throw new RecordNotFoundException("Tag not found");
        }
    }

}
