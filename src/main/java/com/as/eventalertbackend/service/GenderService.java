package com.as.eventalertbackend.service;

import com.as.eventalertbackend.enums.id.GenderId;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.lookup.Gender;
import com.as.eventalertbackend.persistence.reopsitory.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GenderService {

    private final GenderRepository genderRepository;

    @Autowired
    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    Gender findEntityByCode(GenderId genderId) {
        return genderRepository.findById(genderId)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.GENDER_NOT_FOUND));
    }

}
