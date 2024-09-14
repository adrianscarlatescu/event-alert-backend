package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventCommentRequestDto;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.dto.EventCommentDto;
import com.as.eventalertbackend.service.EventCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class EventCommentController {

    private final EventCommentService commentService;

    @Autowired
    public EventCommentController(EventCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<EventCommentDto>> getAllByEventId(@PathVariable("eventId") Long id) {
        List<EventCommentDto> comments = commentService.findAllByEventId(id).stream()
                .map(EventComment::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<EventCommentDto> save(@Valid @RequestBody EventCommentRequestDto commentRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.save(commentRequestDto).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventCommentDto> updateById(@Valid @RequestBody EventCommentRequestDto commentRequestDto,
                                                      @PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.updateById(commentRequestDto, id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
