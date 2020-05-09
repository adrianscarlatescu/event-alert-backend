package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(Role role);

}
