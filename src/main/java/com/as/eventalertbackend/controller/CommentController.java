package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.response.CommentResponse;
import com.as.eventalertbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {


    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<CommentResponse>> getAllByEventId(@PathVariable("eventId") Long id) {
        return ResponseEntity.ok(commentService.findAllByEventId(id));
    }

    /*@PostMapping
    public ResponseEntity<EventCommentResponse> save(@Valid @RequestBody EventCommentRequest commentRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(commentService.save(commentRequest), EventCommentResponse.class));
    }

    @Secured({"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventCommentResponse> updateById(@Valid @RequestBody EventCommentRequest commentRequest,
                                                           @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(commentService.updateById(commentRequest, id), EventCommentResponse.class));
    }*/

    @Secured({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
