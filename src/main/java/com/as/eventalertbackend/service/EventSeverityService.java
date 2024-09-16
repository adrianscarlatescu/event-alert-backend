package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventSeverityRequestDto;
import com.as.eventalertbackend.dto.response.EventSeverityResponseDto;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.EventSeverity;
import com.as.eventalertbackend.jpa.reopsitory.EventSeverityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventSeverityService {

    private final EventSeverityRepository severityRepository;

    @Autowired
    public EventSeverityService(EventSeverityRepository severityRepository) {
        this.severityRepository = severityRepository;
    }

    public List<EventSeverityResponseDto> findAll() {
        return severityRepository.findAll().stream()
                .map(EventSeverity::toDto)
                .collect(Collectors.toList());
    }

    public EventSeverityResponseDto findById(Long id) {
        return severityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND))
                .toDto();
    }

    public EventSeverityResponseDto updateById(EventSeverityRequestDto severityRequestDto, Long id) {
        EventSeverity severity = severityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND));

        severity.setName(severityRequestDto.getName());
        severity.setColor(severityRequestDto.getColor());

        return severityRepository.save(severity).toDto();
    }

    public EventSeverityResponseDto save(EventSeverityRequestDto severityRequestDto) {
        EventSeverity severity = new EventSeverity();

        severity.setName(severityRequestDto.getName());
        severity.setColor(severityRequestDto.getColor());

        return severityRepository.save(severity).toDto();
    }

    public void deleteById(Long id) {
        if (severityRepository.existsById(id)) {
            severityRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND);
        }
    }

}
