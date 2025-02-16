package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.severity.SeverityCreateDTO;
import com.as.eventalertbackend.dto.severity.SeverityDTO;
import com.as.eventalertbackend.dto.severity.SeverityUpdateDTO;
import com.as.eventalertbackend.service.SeverityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/severities")
public class SeverityController {

    private final SeverityService severityService;

    @Autowired
    public SeverityController(SeverityService severityService) {
        this.severityService = severityService;
    }

    @GetMapping
    public ResponseEntity<List<SeverityDTO>> getAll() {
        return ResponseEntity.ok(severityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeverityDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(severityService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<SeverityDTO> save(@Valid @RequestBody SeverityCreateDTO severityCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(severityService.save(severityCreateDTO));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<SeverityDTO> updateById(@Valid @RequestBody SeverityUpdateDTO severityUpdateDTO,
                                                  @PathVariable("id") Long id) {
        return ResponseEntity.ok(severityService.updateById(severityUpdateDTO, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        severityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
