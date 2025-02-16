package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.comment.CommentCreateDTO;
import com.as.eventalertbackend.dto.comment.CommentDTO;
import com.as.eventalertbackend.dto.comment.CommentUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Comment;
import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.persistence.entity.User;
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

    Comment findEntityById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND));
    }

    public List<CommentDTO> findAllByEventId(Long id) {
        return commentRepository.findByEventIdOrderByCreatedAtDesc(id).stream()
                .map(comment -> mapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public CommentDTO save(CommentCreateDTO commentCreateDTO) {
        Comment comment = new Comment();

        Event event = eventService.findEntityById(commentCreateDTO.getEventId());
        User user = userService.findEntityById(commentCreateDTO.getUserId());

        comment.setEvent(event);
        comment.setUser(user);
        comment.setComment(commentCreateDTO.getComment());

        return mapper.map(comment, CommentDTO.class);
    }

    public CommentDTO updateById(CommentUpdateDTO commentUpdateDTO, Long id) {
        Comment comment = findEntityById(id);

        comment.setComment(commentUpdateDTO.getComment());

        return mapper.map(comment, CommentDTO.class);
    }

    public void deleteById(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND);
        }
    }

}
