package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.status.StatusCreateDTO;
import com.as.eventalertbackend.dto.status.StatusDTO;
import com.as.eventalertbackend.dto.status.StatusUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.lookup.Status;
import com.as.eventalertbackend.persistence.reopsitory.StatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatusService {

    private final ModelMapper mapper;

    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(ModelMapper mapper,
                         StatusRepository statusRepository) {
        this.mapper = mapper;
        this.statusRepository = statusRepository;
    }

    Status findEntityById(String id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.STATUS_NOT_FOUND));
    }

    public List<StatusDTO> findAll() {
        return statusRepository.findAllByOrderByPositionAsc().stream()
                .map(status -> mapper.map(status, StatusDTO.class))
                .collect(Collectors.toList());
    }

    public StatusDTO findById(String id) {
        return mapper.map(findEntityById(id), StatusDTO.class);
    }

    public StatusDTO save(StatusCreateDTO statusCreateDTO) {
        Status status = new Status();

        if (statusRepository.existsById(statusCreateDTO.getId())) {
            throw new InvalidActionException(ApiErrorMessage.STATUS_ID_EXISTS);
        }
        if (statusRepository.existsByPositionAndIdNot(statusCreateDTO.getPosition(), statusCreateDTO.getId())) {
            throw new InvalidActionException(ApiErrorMessage.STATUS_POSITION_EXISTS);
        }

        status.setId(statusCreateDTO.getId());
        status.setLabel(statusCreateDTO.getLabel());
        status.setColor(statusCreateDTO.getColor());
        status.setDescription(statusCreateDTO.getDescription());
        status.setPosition(statusCreateDTO.getPosition());

        return mapper.map(statusRepository.save(status), StatusDTO.class);
    }

    public StatusDTO updateById(StatusUpdateDTO statusUpdateDTO, String id) {
        Status status = findEntityById(id);

        if (statusRepository.existsByPositionAndIdNot(statusUpdateDTO.getPosition(), id)) {
            throw new InvalidActionException(ApiErrorMessage.STATUS_POSITION_EXISTS);
        }

        status.setLabel(statusUpdateDTO.getLabel());
        status.setColor(statusUpdateDTO.getColor());
        status.setDescription(statusUpdateDTO.getDescription());
        status.setPosition(statusUpdateDTO.getPosition());

        return mapper.map(status, StatusDTO.class);
    }

    public void deleteById(String id) {
        if (!statusRepository.existsById(id)) {
            throw new RecordNotFoundException(ApiErrorMessage.STATUS_NOT_FOUND);
        }
        if (statusRepository.existsEventByStatusId(id)) {
            throw new InvalidActionException(ApiErrorMessage.STATUS_REFERENCED);
        }
        statusRepository.deleteById(id);
    }

}
