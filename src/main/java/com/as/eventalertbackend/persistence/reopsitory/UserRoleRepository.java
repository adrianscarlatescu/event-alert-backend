package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.enums.UserRoleCode;
import com.as.eventalertbackend.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByCode(UserRoleCode userRoleCode);

    Set<UserRole> findAllByCodeIn(Set<UserRoleCode> userRoleCodes);

}
