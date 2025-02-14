package com.as.eventalertbackend.service;

import com.as.eventalertbackend.model.UserRoleCode;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.UserRole;
import com.as.eventalertbackend.persistence.reopsitory.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository repository;

    @Autowired
    public UserRoleService(UserRoleRepository repository) {
        this.repository = repository;
    }

    UserRole findEntityByCode(UserRoleCode userRoleCode) {
        return repository.findByCode(userRoleCode)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.ROLE_NOT_FOUND));
    }

    Set<UserRole> findAllEntitiesByCode(Set<UserRoleCode> userRoleCodes) {
        return repository.findAllByCodeIn(userRoleCodes);
    }

}
