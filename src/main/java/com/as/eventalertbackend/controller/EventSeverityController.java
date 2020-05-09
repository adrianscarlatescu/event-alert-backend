package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.service.EventSeverityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/severities")
public class EventSeverityController {

    private final EventSeverityService service;

    @Autowired
    public EventSeverityController(EventSeverityService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventSeverity> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSeverity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventSeverity> save(@Valid @RequestBody EventSeverity severity) {
        return ResponseEntity.ok(service.save(severity));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventSeverity> updateById(@Valid @RequestBody EventSeverity severity, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.updateById(severity, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

}
