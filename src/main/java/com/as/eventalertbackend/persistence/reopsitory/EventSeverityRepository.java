package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.EventSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSeverityRepository extends JpaRepository<EventSeverity, Long> {

}
