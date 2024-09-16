package com.as.eventalertbackend.jpa.reopsitory;

import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.jpa.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(Role role);

    Set<UserRole> findAllByNameIn(Set<Role> roles);

}
