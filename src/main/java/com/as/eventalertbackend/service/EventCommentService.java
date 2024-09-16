package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.EventCommentRequestDto;
import com.as.eventalertbackend.dto.response.EventCommentResponseDto;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.Event;
import com.as.eventalertbackend.jpa.entity.EventComment;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.reopsitory.EventCommentRepository;
import com.as.eventalertbackend.jpa.reopsitory.EventRepository;
import com.as.eventalertbackend.jpa.reopsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventCommentService {

    private final EventCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventCommentService(EventCommentRepository commentRepository,
                               UserRepository userRepository,
                               EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public EventCommentResponseDto findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND))
                .toDto();
    }

    public List<EventCommentResponseDto> findAllByEventId(Long id) {
        return commentRepository.findByEventIdOrderByDateTimeDesc(id).stream()
                .map(EventComment::toDto)
                .collect(Collectors.toList());
    }

    public EventCommentResponseDto save(EventCommentRequestDto commentRequestDto) {
        return createOrUpdate(new EventComment(), commentRequestDto).toDto();
    }

    public EventCommentResponseDto updateById(EventCommentRequestDto commentRequestDto, Long id) {
        EventComment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND));
        return createOrUpdate(comment, commentRequestDto).toDto();
    }

    public void deleteById(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND);
        }
    }

    private EventComment createOrUpdate(EventComment comment, EventCommentRequestDto commentRequestDto) {
        Event event = eventRepository.findById(commentRequestDto.getEventId())
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND));

        User user = userRepository.findById(commentRequestDto.getEventId())
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND));

        comment.setEvent(event);
        comment.setUser(user);
        comment.setComment(commentRequestDto.getComment());

        return commentRepository.save(comment);
    }

}
