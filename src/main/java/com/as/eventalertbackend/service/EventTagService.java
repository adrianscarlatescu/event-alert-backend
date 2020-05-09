package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.data.reopsitory.EventTagRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTagService {

    private final EventTagRepository repository;

    @Autowired
    public EventTagService(EventTagRepository repository) {
        this.repository = repository;
    }

    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EventTag> findAll() {
        return repository.findAll();
    }

    public EventTag findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No record for tag " + id,
                        "Tag not found"));
    }

    public EventTag updateById(EventTag tag, Long id) {
        EventTag dbObj = findById(id);
        dbObj.setName(tag.getName());
        dbObj.setImagePath(tag.getImagePath());
        return repository.save(dbObj);
    }

    public EventTag save(EventTag tag) {
        return repository.save(tag);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException(
                    "No record for tag " + id,
                    "Tag not found");
        }
    }

}
