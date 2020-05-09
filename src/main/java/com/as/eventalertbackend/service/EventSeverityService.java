package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.reopsitory.EventSeverityRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventSeverityService {

    private final EventSeverityRepository repository;

    @Autowired
    public EventSeverityService(EventSeverityRepository repository) {
        this.repository = repository;
    }

    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EventSeverity> findAll() {
        return repository.findAll();
    }

    public EventSeverity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No record for severity " + id,
                        "Severity not found"));
    }

    public EventSeverity updateById(EventSeverity severity, Long id) {
        EventSeverity dbObj = findById(id);
        dbObj.setName(severity.getName());
        dbObj.setColor(severity.getColor());
        return repository.save(dbObj);
    }

    public EventSeverity save(EventSeverity severity) {
        return repository.save(severity);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException(
                    "No record for severity " + id,
                    "Severity not found");
        }
    }

}
