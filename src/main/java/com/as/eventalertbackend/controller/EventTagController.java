package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventTagRequestDto;
import com.as.eventalertbackend.dto.response.EventTagResponseDto;
import com.as.eventalertbackend.service.EventTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class EventTagController {

    private final EventTagService tagService;

    @Autowired
    public EventTagController(EventTagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<EventTagResponseDto>> getAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTagResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventTagResponseDto> save(@Valid @RequestBody EventTagRequestDto tagRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.save(tagRequestDto));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventTagResponseDto> updateById(@Valid @RequestBody EventTagRequestDto tagRequestDto,
                                                          @PathVariable("id") Long id) {
        return ResponseEntity.ok(tagService.updateById(tagRequestDto, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

