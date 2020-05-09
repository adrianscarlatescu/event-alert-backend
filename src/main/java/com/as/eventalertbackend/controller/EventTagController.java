package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.service.EventTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class EventTagController {

    private final EventTagService service;

    @Autowired
    public EventTagController(EventTagService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventTag> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTag> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventTag> save(@Valid @RequestBody EventTag tag) {
        return ResponseEntity.ok(service.save(tag));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventTag> updateById(@Valid @RequestBody EventTag tag, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.updateById(tag, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

}

