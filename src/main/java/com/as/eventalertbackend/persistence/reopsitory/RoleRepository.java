package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.model.RoleCode;
import com.as.eventalertbackend.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(RoleCode roleCode);

    List<Role> findAllByCodeIn(List<RoleCode> roleCodes);

}
