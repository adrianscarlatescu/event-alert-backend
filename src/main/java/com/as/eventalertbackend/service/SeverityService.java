package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.severity.SeverityCreateDTO;
import com.as.eventalertbackend.dto.severity.SeverityDTO;
import com.as.eventalertbackend.dto.severity.SeverityUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
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

    public List<SeverityDTO> findAll() {
        return severityRepository.findAll().stream()
                .map(severity -> mapper.map(severity, SeverityDTO.class))
                .collect(Collectors.toList());
    }

    public SeverityDTO findById(Long id) {
        return mapper.map(findEntityById(id), SeverityDTO.class);
    }

    public SeverityDTO save(SeverityCreateDTO severityCreateDTO) {
        Severity severity = createOrUpdate(severityCreateDTO, null);
        return mapper.map(severityRepository.save(severity), SeverityDTO.class);
    }

    public SeverityDTO updateById(SeverityUpdateDTO severityUpdateDTO, Long id) {
        Severity severity = createOrUpdate(severityUpdateDTO, id);
        return mapper.map(severity, SeverityDTO.class);
    }

    private <T extends SeverityCreateDTO> Severity createOrUpdate(T createOrUpdateDTO, Long severityId) {
        Severity severity = severityId == null ? new Severity() : findEntityById(severityId);

        if (severityRepository.existsByCode(createOrUpdateDTO.getCode())) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_CODE_EXISTS);
        }

        severity.setCode(createOrUpdateDTO.getCode());
        severity.setLabel(createOrUpdateDTO.getLabel());
        severity.setColor(createOrUpdateDTO.getColor());

        return severity;
    }

    public void deleteById(Long id) {
        if (!severityRepository.existsById(id)) {
            throw new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND);
        }
        if (severityRepository.existsEventBySeverityId(id)) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_REFERENCED);
        }
        severityRepository.deleteById(id);
    }

}
