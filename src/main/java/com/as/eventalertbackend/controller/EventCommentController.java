package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventCommentRequestDto;
import com.as.eventalertbackend.dto.response.EventCommentDto;
import com.as.eventalertbackend.jpa.entity.EventComment;
import com.as.eventalertbackend.mapper.Mapper;
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

    private final Mapper<EventComment, EventCommentDto> mapper;
    private final EventCommentService commentService;

    @Autowired
    public EventCommentController(Mapper<EventComment, EventCommentDto> mapper,
                                  EventCommentService commentService) {
        this.mapper = mapper;
        this.commentService = commentService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<EventCommentDto>> getAllByEventId(@PathVariable("eventId") Long id) {
        return ResponseEntity.ok(mapper.mapTo(commentService.findAllByEventId(id)));
    }

    @PostMapping
    public ResponseEntity<EventCommentDto> save(@Valid @RequestBody EventCommentRequestDto commentRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapTo(commentService.save(commentRequestDto)));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventCommentDto> updateById(@Valid @RequestBody EventCommentRequestDto commentRequestDto,
                                                      @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.mapTo(commentService.updateById(commentRequestDto, id)));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
