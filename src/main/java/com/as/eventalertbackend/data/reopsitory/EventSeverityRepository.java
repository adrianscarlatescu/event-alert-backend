package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.EventSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSeverityRepository extends JpaRepository<EventSeverity, Long> {

}