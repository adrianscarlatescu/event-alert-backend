package com.as.eventalertbackend.service;

import com.as.eventalertbackend.model.RoleCode;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Role;
import com.as.eventalertbackend.persistence.reopsitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    Role findEntityByCode(RoleCode roleCode) {
        return repository.findByCode(roleCode)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.ROLE_NOT_FOUND));
    }

    Set<Role> findAllEntitiesByCode(Set<RoleCode> roleCodes) {
        return repository.findAllByCodeIn(roleCodes);
    }

}
