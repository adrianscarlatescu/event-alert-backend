package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.gender.GenderDTO;
import com.as.eventalertbackend.enums.id.GenderId;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Gender;
import com.as.eventalertbackend.persistence.reopsitory.GenderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GenderService {

    private final ModelMapper mapper;

    private final GenderRepository genderRepository;

    @Autowired
    public GenderService(ModelMapper mapper,
                         GenderRepository genderRepository) {
        this.mapper = mapper;
        this.genderRepository = genderRepository;
    }

    Gender findEntityByCode(GenderId genderId) {
        return genderRepository.findById(genderId)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.GENDER_NOT_FOUND));
    }

    public List<GenderDTO> findAll() {
        return genderRepository.findAllByOrderByPositionAsc().stream()
                .map(gender -> mapper.map(gender, GenderDTO.class))
                .collect(Collectors.toList());
    }

}
