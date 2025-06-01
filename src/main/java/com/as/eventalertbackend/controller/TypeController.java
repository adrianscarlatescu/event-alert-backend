package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.type.TypeCreateDTO;
import com.as.eventalertbackend.dto.type.TypeDTO;
import com.as.eventalertbackend.dto.type.TypeUpdateDTO;
import com.as.eventalertbackend.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<TypeDTO>> getAll() {
        return ResponseEntity.ok(typeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(typeService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<TypeDTO> save(@Valid @RequestBody TypeCreateDTO typeCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(typeService.save(typeCreateDTO));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<TypeDTO> updateById(@Valid @RequestBody TypeUpdateDTO typeUpdateDTO,
                                              @PathVariable("id") String id) {
        return ResponseEntity.ok(typeService.updateById(typeUpdateDTO, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        typeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

