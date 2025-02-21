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

    Severity findEntityById(String id) {
        return severityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND));
    }

    public List<SeverityDTO> findAll() {
        return severityRepository.findAllByOrderByPositionAsc().stream()
                .map(severity -> mapper.map(severity, SeverityDTO.class))
                .collect(Collectors.toList());
    }

    public SeverityDTO findById(String id) {
        return mapper.map(findEntityById(id), SeverityDTO.class);
    }

    public SeverityDTO save(SeverityCreateDTO severityCreateDTO) {
        Severity severity = new Severity();

        if (severityRepository.existsById(severityCreateDTO.getId())) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_ID_EXISTS);
        }
        if (severityRepository.existsByPosition(severityCreateDTO.getPosition())) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_POSITION_EXISTS);
        }

        severity.setId(severityCreateDTO.getId());
        severity.setLabel(severityCreateDTO.getLabel());
        severity.setColor(severityCreateDTO.getColor());
        severity.setPosition(severityCreateDTO.getPosition());

        return mapper.map(severityRepository.save(severity), SeverityDTO.class);
    }

    public SeverityDTO updateById(SeverityUpdateDTO severityUpdateDTO, String id) {
        Severity severity = findEntityById(id);

        if (severityRepository.existsByPositionAndIdIsNot(severityUpdateDTO.getPosition(), id)) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_POSITION_EXISTS);
        }

        severity.setLabel(severityUpdateDTO.getLabel());
        severity.setColor(severityUpdateDTO.getColor());
        severity.setPosition(severityUpdateDTO.getPosition());

        return mapper.map(severity, SeverityDTO.class);
    }

    public void deleteById(String id) {
        if (!severityRepository.existsById(id)) {
            throw new RecordNotFoundException(ApiErrorMessage.SEVERITY_NOT_FOUND);
        }
        if (severityRepository.existsEventBySeverityId(id)) {
            throw new InvalidActionException(ApiErrorMessage.SEVERITY_REFERENCED);
        }
        severityRepository.deleteById(id);
    }

}
