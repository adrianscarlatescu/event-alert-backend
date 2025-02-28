package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.category.CategoryBaseDTO;
import com.as.eventalertbackend.dto.category.CategoryCreateDTO;
import com.as.eventalertbackend.dto.category.CategoryDTO;
import com.as.eventalertbackend.dto.category.CategoryUpdateDTO;
import com.as.eventalertbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<CategoryBaseDTO> save(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.save(categoryCreateDTO));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<CategoryBaseDTO> updateById(@Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO,
                                                  @PathVariable("id") String id) {
        return ResponseEntity.ok(categoryService.updateById(categoryUpdateDTO, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByCode(@PathVariable("id") String id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
