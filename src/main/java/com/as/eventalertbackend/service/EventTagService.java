package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventTagRequest;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.persistence.entity.EventTag;
import com.as.eventalertbackend.persistence.reopsitory.EventTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTagService {

    private final EventTagRepository tagRepository;

    private final StorageService storageService;

    @Autowired
    public EventTagService(EventTagRepository tagRepository,
                           StorageService storageService) {
        this.tagRepository = tagRepository;
        this.storageService = storageService;
    }

    public List<EventTag> findAll() {
        return tagRepository.findAll();
    }

    public EventTag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.TAG_NOT_FOUND));
    }

    public EventTag updateById(EventTagRequest tagRequest, Long id) {
        return createOrUpdate(findById(id), tagRequest);
    }

    public EventTag save(EventTagRequest tagRequest) {
        return createOrUpdate(new EventTag(), tagRequest);
    }

    public void deleteById(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.TAG_NOT_FOUND);
        }
    }

    private EventTag createOrUpdate(EventTag tag, EventTagRequest tagRequest) {
        if (!storageService.imageExists(tag.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        tag.setName(tagRequest.getName());
        tag.setImagePath(tagRequest.getImagePath());

        return tagRepository.save(tag);
    }

}
