package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventCommentRequestDto;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.Event;
import com.as.eventalertbackend.jpa.entity.EventComment;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.reopsitory.EventCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public EventComment save(EventCommentRequestDto commentRequestDto) {
        return createOrUpdate(new EventComment(), commentRequestDto);
    }

    public EventComment updateById(EventCommentRequestDto commentRequestDto, Long id) {
        EventComment comment = findById(id);
        return createOrUpdate(comment, commentRequestDto);
    }

    public void deleteById(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND);
        }
    }

    private EventComment createOrUpdate(EventComment comment, EventCommentRequestDto commentRequestDto) {
        Event event = eventService.findById(commentRequestDto.getEventId());
        User user = userService.findById(commentRequestDto.getEventId());

        comment.setEvent(event);
        comment.setUser(user);
        comment.setComment(commentRequestDto.getComment());

        return commentRepository.save(comment);
    }

}
