package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagRepository extends JpaRepository<EventType, Long> {

}
