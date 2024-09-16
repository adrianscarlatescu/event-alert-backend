package com.as.eventalertbackend.jpa.reopsitory;

import com.as.eventalertbackend.jpa.entity.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagRepository extends JpaRepository<EventTag, Long> {

}
