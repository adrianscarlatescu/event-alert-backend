package com.as.eventalertbackend.service;

import com.as.eventalertbackend.enums.id.RoleId;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Role;
import com.as.eventalertbackend.persistence.reopsitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    Role findEntityByCode(RoleId roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.ROLE_NOT_FOUND));
    }

    List<Role> findAllEntitiesByCode(List<RoleId> roleIds) {
        return roleRepository.findAllById(roleIds);
    }

}
