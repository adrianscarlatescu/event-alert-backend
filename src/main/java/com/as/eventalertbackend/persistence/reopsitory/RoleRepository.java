package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.enums.id.RoleId;
import com.as.eventalertbackend.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, RoleId> {

    List<Role> findAllByOrderByPositionAsc();

}
