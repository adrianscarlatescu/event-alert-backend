package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagRepository extends JpaRepository<EventTag, Long> {

}