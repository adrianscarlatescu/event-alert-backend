package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.status.StatusCreateDTO;
import com.as.eventalertbackend.dto.status.StatusDTO;
import com.as.eventalertbackend.dto.status.StatusUpdateDTO;
import com.as.eventalertbackend.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<StatusDTO>> getAll() {
        return ResponseEntity.ok(statusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(statusService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<StatusDTO> save(@Valid @RequestBody StatusCreateDTO statusCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(statusService.save(statusCreateDTO));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<StatusDTO> updateById(@Valid @RequestBody StatusUpdateDTO statusUpdateDTO,
                                                @PathVariable("id") String id) {
        return ResponseEntity.ok(statusService.updateById(statusUpdateDTO, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        statusService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
