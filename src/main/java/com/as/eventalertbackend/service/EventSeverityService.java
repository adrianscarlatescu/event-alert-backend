package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventSeverityRequestDto;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.reopsitory.EventSeverityRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
                .orElseThrow(() -> new RecordNotFoundException("Severity not found"));
    }

    public EventSeverity updateById(EventSeverityRequestDto severityRequestDto, Long id) {
        EventSeverity severity = findById(id);
        severity.setName(severityRequestDto.getName());
        severity.setColor(severityRequestDto.getColor());
        return severityRepository.save(severity);
    }

    public EventSeverity save(EventSeverityRequestDto severityRequestDto) {
        EventSeverity severity = new EventSeverity();
        severity.setName(severityRequestDto.getName());
        severity.setColor(severityRequestDto.getColor());
        return severityRepository.save(severity);
    }

    public void deleteById(Long id) {
        if (severityRepository.existsById(id)) {
            severityRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Severity not found");
        }
    }

}
