package com.as.eventalertbackend.jpa.reopsitory;

import com.as.eventalertbackend.jpa.entity.EventSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSeverityRepository extends JpaRepository<EventSeverity, Long> {

}
