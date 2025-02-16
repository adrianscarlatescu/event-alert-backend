package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.comment.CommentCreateDTO;
import com.as.eventalertbackend.dto.comment.CommentDTO;
import com.as.eventalertbackend.dto.comment.CommentUpdateDTO;
import com.as.eventalertbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<CommentDTO>> getAllByEventId(@PathVariable("eventId") Long id) {
        return ResponseEntity.ok(commentService.findAllByEventId(id));
    }

    @PostMapping
    public ResponseEntity<CommentDTO> save(@Valid @RequestBody CommentCreateDTO commentCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.save(commentCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateById(@Valid @RequestBody CommentUpdateDTO commentUpdateDTO,
                                                 @PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.updateById(commentUpdateDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
