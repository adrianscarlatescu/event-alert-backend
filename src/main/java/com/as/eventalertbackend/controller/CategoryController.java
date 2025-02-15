package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.response.CategoryResponse;
import com.as.eventalertbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
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
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
