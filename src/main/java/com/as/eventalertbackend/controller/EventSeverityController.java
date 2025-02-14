package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.response.EventSeverityResponse;
import com.as.eventalertbackend.service.EventSeverityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/severities")
public class EventSeverityController {

    private final EventSeverityService severityService;

    @Autowired
    public EventSeverityController(EventSeverityService severityService) {
        this.severityService = severityService;
    }

    @GetMapping
    public ResponseEntity<List<EventSeverityResponse>> getAll() {
        return ResponseEntity.ok(severityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSeverityResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(severityService.findById(id));
    }

    /*@Secured({"ADMIN"})
    @PostMapping
    public ResponseEntity<EventSeverityResponse> save(@Valid @RequestBody EventSeverityUpdateRequest severityRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(severityService.save(severityRequest), EventSeverityResponse.class));
    }

    @Secured({"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventSeverityResponse> updateById(@Valid @RequestBody EventSeverityUpdateRequest severityRequest,
                                                            @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(severityService.updateById(severityRequest, id), EventSeverityResponse.class));
    }*/

    @Secured({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        severityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
