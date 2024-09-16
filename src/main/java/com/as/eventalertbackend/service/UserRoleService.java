package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.response.UserRoleResponseDto;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.UserRole;
import com.as.eventalertbackend.jpa.reopsitory.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRoleResponseDto findByName(Role role) {
        return userRoleRepository.findByName(role)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.ROLE_NOT_FOUND))
                .toDto();
    }

    public Set<UserRoleResponseDto> findAllByName(Set<Role> roles) {
        return userRoleRepository.findAllByNameIn(roles).stream()
                .map(UserRole::toDto)
                .collect(Collectors.toSet());
    }

}
