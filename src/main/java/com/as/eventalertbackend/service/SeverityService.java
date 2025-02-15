package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.response.SeverityResponse;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Severity;
import com.as.eventalertbackend.persistence.reopsitory.SeverityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeverityService {

    private final ModelMapper mapper;

    private final SeverityRepository severityRepository;

    @Autowired
    public SeverityService(ModelMapper mapper,
                           SeverityRepository severityRepository) {
        this.mapper = mapper;
        this.severityRepository = severityRepository;
    }

    Severity findEntityById(Long id) {
        return severityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND));
    }

    public List<SeverityResponse> findAll() {
        return severityRepository.findAll().stream()
                .map(severity -> mapper.map(severity, SeverityResponse.class))
                .collect(Collectors.toList());
    }

    public SeverityResponse findById(Long id) {
        return mapper.map(findEntityById(id), SeverityResponse.class);
    }

    /*public EventSeverity save(EventSeverityUpdateRequest severityRequest) {
        return severityRepository.save(createOrUpdate(new EventSeverity(), severityRequest));
    }

    public EventSeverity updateById(EventSeverityUpdateRequest severityRequest, Long id) {
        return createOrUpdate(findEntityById(id), severityRequest);
    }*/

    public void deleteById(Long id) {
        if (severityRepository.existsById(id)) {
            severityRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND);
        }
    }

    /*private EventSeverity createOrUpdate(EventSeverity severity, EventSeverityUpdateRequest severityRequest) {
        severity.setName(severityRequest.getName());
        severity.setColor(severityRequest.getColor());

        return severity;
    }*/

}
