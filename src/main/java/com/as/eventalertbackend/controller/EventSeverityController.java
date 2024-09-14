package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventSeverityRequestDto;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.dto.EventSeverityDto;
import com.as.eventalertbackend.service.EventSeverityService;
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

    private final EventSeverityService severityService;

    @Autowired
    public EventSeverityController(EventSeverityService severityService) {
        this.severityService = severityService;
    }

    @GetMapping
    public ResponseEntity<List<EventSeverityDto>> getAll() {
        List<EventSeverityDto> severities = severityService.findAll().stream()
                .map(EventSeverity::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(severities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSeverityDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(severityService.findById(id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventSeverityDto> save(@Valid @RequestBody EventSeverityRequestDto severityRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(severityService.save(severityRequestDto).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventSeverityDto> updateById(@Valid @RequestBody EventSeverityRequestDto severityRequestDto,
                                                       @PathVariable("id") Long id) {
        return ResponseEntity.ok(severityService.updateById(severityRequestDto, id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        severityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
