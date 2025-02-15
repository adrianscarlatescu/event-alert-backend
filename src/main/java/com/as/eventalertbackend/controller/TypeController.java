package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.response.TypeResponse;
import com.as.eventalertbackend.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeResponse>> getAll() {
        return ResponseEntity.ok(typeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(typeService.findById(id));
    }

    /*@Secured({"ADMIN"})
    @PostMapping
    public ResponseEntity<EventTypeResponse> save(@Valid @RequestBody EventTypeUpdateRequest typeUpdateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(typeService.save(typeUpdateRequest), EventTypeResponse.class));
    }

    @Secured({"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventTypeResponse> updateById(@Valid @RequestBody EventTypeUpdateRequest tagRequest,
                                                        @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(typeService.updateById(tagRequest, id), EventTypeResponse.class));
    }*/

    @Secured({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        typeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

