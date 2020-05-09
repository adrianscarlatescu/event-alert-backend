package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.data.reopsitory.UserRoleRepository;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;

    @Autowired
    public UserRoleService(UserRoleRepository repository) {
        this.repository = repository;
    }

    public UserRole findByName(Role role) {
        return repository.findByName(role)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No record for role " + role,
                        "The role was not found"));
    }

}
