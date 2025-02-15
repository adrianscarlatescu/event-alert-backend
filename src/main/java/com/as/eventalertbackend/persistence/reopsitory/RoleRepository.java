package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.model.RoleCode;
import com.as.eventalertbackend.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(RoleCode roleCode);

    Set<Role> findAllByCodeIn(Set<RoleCode> roleCodes);

}
