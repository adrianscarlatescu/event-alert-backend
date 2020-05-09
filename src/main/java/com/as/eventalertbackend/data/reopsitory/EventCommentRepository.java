package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.EventComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCommentRepository extends JpaRepository<EventComment, Long> {

    List<EventComment> getByEventIdOrderByDateTimeDesc(Long id);

}
