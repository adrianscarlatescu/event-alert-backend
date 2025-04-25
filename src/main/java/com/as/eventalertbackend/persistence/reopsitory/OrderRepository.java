package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.enums.id.RoleId;
import com.as.eventalertbackend.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, RoleId> {

    List<Order> findAllByOrderByPositionAsc();

}
