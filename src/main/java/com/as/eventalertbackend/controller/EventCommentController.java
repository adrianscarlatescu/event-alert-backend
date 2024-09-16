package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventCommentRequestDto;
import com.as.eventalertbackend.dto.response.EventCommentResponseDto;
import com.as.eventalertbackend.service.EventCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class EventCommentController {

    private final EventCommentService commentService;

    @Autowired
    public EventCommentController(EventCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<EventCommentResponseDto>> getAllByEventId(@PathVariable("eventId") Long id) {
        return ResponseEntity.ok(commentService.findAllByEventId(id));
    }

    @PostMapping
    public ResponseEntity<EventCommentResponseDto> save(@Valid @RequestBody EventCommentRequestDto commentRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.save(commentRequestDto));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventCommentResponseDto> updateById(@Valid @RequestBody EventCommentRequestDto commentRequestDto,
                                                              @PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.updateById(commentRequestDto, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
