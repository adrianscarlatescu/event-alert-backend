package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventSeverityRequestDto;
import com.as.eventalertbackend.dto.response.EventSeverityDto;
import com.as.eventalertbackend.jpa.entity.EventSeverity;
import com.as.eventalertbackend.mapper.Mapper;
import com.as.eventalertbackend.service.EventSeverityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/severities")
public class EventSeverityController {

    private final Mapper<EventSeverity, EventSeverityDto> mapper;
    private final EventSeverityService severityService;

    @Autowired
    public EventSeverityController(Mapper<EventSeverity, EventSeverityDto> mapper,
                                   EventSeverityService severityService) {
        this.mapper = mapper;
        this.severityService = severityService;
    }

    @GetMapping
    public ResponseEntity<List<EventSeverityDto>> getAll() {
        return ResponseEntity.ok(mapper.mapTo(severityService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSeverityDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.mapTo(severityService.findById(id)));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventSeverityDto> save(@Valid @RequestBody EventSeverityRequestDto severityRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapTo(severityService.save(severityRequestDto)));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventSeverityDto> updateById(@Valid @RequestBody EventSeverityRequestDto severityRequestDto,
                                                       @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.mapTo(severityService.updateById(severityRequestDto, id)));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        severityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
