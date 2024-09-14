package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.data.reopsitory.UserRoleRepository;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole findByName(Role role) {
        return userRoleRepository.findByName(role)
                .orElseThrow(() -> new RecordNotFoundException("The role was not found"));
    }

    public Set<UserRole> findAllByName(Set<Role> roles) {
        return userRoleRepository.findAllByNameIn(roles);
    }

}
