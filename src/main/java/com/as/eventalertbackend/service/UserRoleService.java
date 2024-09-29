package com.as.eventalertbackend.service;

import com.as.eventalertbackend.enums.Role;
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

    public UserRole findByName(Role role) {
        return repository.findByName(role)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.ROLE_NOT_FOUND));
    }

    public Set<UserRole> findAllByName(Set<Role> roles) {
        return repository.findAllByNameIn(roles);
    }

}
