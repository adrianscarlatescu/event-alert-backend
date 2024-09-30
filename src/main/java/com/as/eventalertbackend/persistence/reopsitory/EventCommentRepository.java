package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.EventComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCommentRepository extends JpaRepository<EventComment, Long> {

    List<EventComment> findByEventIdOrderByDateTimeDesc(Long id);

}
