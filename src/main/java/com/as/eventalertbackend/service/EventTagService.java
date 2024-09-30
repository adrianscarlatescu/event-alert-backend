package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventTagRequest;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.persistence.entity.EventTag;
import com.as.eventalertbackend.persistence.reopsitory.EventTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventTagService {

    private final EventTagRepository tagRepository;

    private final FileService fileService;

    @Autowired
    public EventTagService(EventTagRepository tagRepository,
                           FileService fileService) {
        this.tagRepository = tagRepository;
        this.fileService = fileService;
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
        if (!fileService.imageExists(tagRequest.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        tag.setName(tagRequest.getName());
        tag.setImagePath(tagRequest.getImagePath());

        return tagRepository.save(tag);
    }

}
