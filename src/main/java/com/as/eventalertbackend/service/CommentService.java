package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.response.CommentResponse;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Comment;
import com.as.eventalertbackend.persistence.reopsitory.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    private final ModelMapper mapper;

    private final CommentRepository commentRepository;

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public CommentService(ModelMapper mapper,
                          CommentRepository commentRepository,
                          EventService eventService,
                          UserService userService) {
        this.mapper = mapper;
        this.commentRepository = commentRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND));
    }

    public List<CommentResponse> findAllByEventId(Long id) {
        return commentRepository.findByEventIdOrderByCreatedAtDesc(id).stream()
                .map(comment -> mapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

    /*public EventComment save(EventCommentRequest commentRequest) {
        return commentRepository.save(createOrUpdate(new EventComment(), commentRequest));
    }

    public EventComment updateById(EventCommentRequest commentRequest, Long id) {
        return createOrUpdate(findById(id), commentRequest);
    }*/

    public void deleteById(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND);
        }
    }

    /*private EventComment createOrUpdate(EventComment comment, EventCommentRequest commentRequest) {
        Event event = eventService.findEntityById(commentRequest.getEventId());
        User user = userService.findEntityById(commentRequest.getUserId());

        comment.setEvent(event);
        comment.setUser(user);
        comment.setComment(commentRequest.getComment());

        return comment;
    }*/

}
