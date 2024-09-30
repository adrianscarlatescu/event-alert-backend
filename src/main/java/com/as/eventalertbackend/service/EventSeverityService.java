package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventSeverityRequest;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.EventSeverity;
import com.as.eventalertbackend.persistence.reopsitory.EventSeverityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventSeverityService {

    private final EventSeverityRepository severityRepository;

    @Autowired
    public EventSeverityService(EventSeverityRepository severityRepository) {
        this.severityRepository = severityRepository;
    }

    public List<EventSeverity> findAll() {
        return severityRepository.findAll();
    }

    public EventSeverity findById(Long id) {
        return severityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND));
    }

    public EventSeverity updateById(EventSeverityRequest severityRequest, Long id) {
        return createOrUpdate(findById(id), severityRequest);
    }

    public EventSeverity save(EventSeverityRequest severityRequest) {
        return createOrUpdate(new EventSeverity(), severityRequest);
    }

    public void deleteById(Long id) {
        if (severityRepository.existsById(id)) {
            severityRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND);
        }
    }

    private EventSeverity createOrUpdate(EventSeverity severity, EventSeverityRequest severityRequest) {
        severity.setName(severityRequest.getName());
        severity.setColor(severityRequest.getColor());

        return severityRepository.save(severity);
    }

}
