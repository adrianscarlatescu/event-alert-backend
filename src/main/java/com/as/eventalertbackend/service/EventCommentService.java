package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.EventCommentRequestDto;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.data.reopsitory.EventCommentRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventCommentService {

    private final EventCommentRepository commentRepository;

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public EventCommentService(EventCommentRepository commentRepository, UserService userService, EventService eventService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    public EventComment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Comment not found"));
    }

    public List<EventComment> findAllByEventId(Long id) {
        return commentRepository.findByEventIdOrderByDateTimeDesc(id);
    }

    public EventComment save(EventCommentRequestDto commentRequestDto) {
        return update(new EventComment(), commentRequestDto);
    }

    public EventComment updateById(EventCommentRequestDto commentRequestDto, Long id) {
        return update(findById(id), commentRequestDto);
    }

    public void deleteById(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Comment not found");
        }
    }

    private EventComment update(EventComment comment, EventCommentRequestDto commentRequestDto) {
        comment.setEvent(eventService.findById(commentRequestDto.getEventId()));
        comment.setUser(userService.findById(commentRequestDto.getUserId()));
        comment.setComment(commentRequestDto.getComment());
        return commentRepository.save(comment);
    }

}
