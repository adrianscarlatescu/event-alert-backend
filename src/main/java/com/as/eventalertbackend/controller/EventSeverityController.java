package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventSeverityRequest;
import com.as.eventalertbackend.dto.response.EventSeverityResponse;
import com.as.eventalertbackend.service.EventSeverityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/severities")
public class EventSeverityController {

    private final ModelMapper mapper;
    private final EventSeverityService severityService;

    @Autowired
    public EventSeverityController(ModelMapper mapper,
                                   EventSeverityService severityService) {
        this.mapper = mapper;
        this.severityService = severityService;
    }

    @GetMapping
    public ResponseEntity<List<EventSeverityResponse>> getAll() {
        return ResponseEntity.ok(
                severityService.findAll().stream()
                        .map(severity -> mapper.map(severity, EventSeverityResponse.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSeverityResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(severityService.findById(id), EventSeverityResponse.class));
    }

    @Secured({"ADMIN"})
    @PostMapping
    public ResponseEntity<EventSeverityResponse> save(@Valid @RequestBody EventSeverityRequest severityRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(severityService.save(severityRequest), EventSeverityResponse.class));
    }

    @Secured({"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventSeverityResponse> updateById(@Valid @RequestBody EventSeverityRequest severityRequest,
                                                            @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(severityService.updateById(severityRequest, id), EventSeverityResponse.class));
    }

    @Secured({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        severityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
