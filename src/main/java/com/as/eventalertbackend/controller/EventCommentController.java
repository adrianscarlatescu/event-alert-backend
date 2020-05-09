package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventCommentBody;
import com.as.eventalertbackend.data.model.EventComment;
import com.as.eventalertbackend.service.EventCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class EventCommentController {

    private final EventCommentService service;

    @Autowired
    public EventCommentController(EventCommentService service) {
        this.service = service;
    }

    @GetMapping("/{eventId}")
    public List<EventComment> getByEventId(@PathVariable("eventId") Long id) {
        return service.findByEventId(id);
    }

    @PostMapping
    public ResponseEntity<EventComment> save(@Valid @RequestBody EventCommentBody body) {
        return ResponseEntity.ok(service.save(body));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventComment> updateById(@Valid @RequestBody EventCommentBody body, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.updateById(body, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

}
