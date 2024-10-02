package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventCommentRequest;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.persistence.entity.EventComment;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.reopsitory.EventCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventCommentService {

    private final EventCommentRepository commentRepository;

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventCommentService(EventCommentRepository commentRepository,
                               EventService eventService,
                               UserService userService) {
        this.commentRepository = commentRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    public EventComment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND));
    }

    public List<EventComment> findAllByEventId(Long id) {
        return commentRepository.findByEventIdOrderByDateTimeDesc(id);
    }

    public EventComment save(EventCommentRequest commentRequest) {
        return commentRepository.save(createOrUpdate(new EventComment(), commentRequest));
    }

    public EventComment updateById(EventCommentRequest commentRequest, Long id) {
        return createOrUpdate(findById(id), commentRequest);
    }

    public void deleteById(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND);
        }
    }

    private EventComment createOrUpdate(EventComment comment, EventCommentRequest commentRequest) {
        Event event = eventService.findById(commentRequest.getEventId());
        User user = userService.findById(commentRequest.getUserId());

        comment.setEvent(event);
        comment.setUser(user);
        comment.setComment(commentRequest.getComment());

        return comment;
    }

}
