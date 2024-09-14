package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventTagRequestDto;
import com.as.eventalertbackend.data.model.EventTag;
import com.as.eventalertbackend.dto.EventTagDto;
import com.as.eventalertbackend.service.EventTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class EventTagController {

    private final EventTagService tagService;

    @Autowired
    public EventTagController(EventTagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<EventTagDto>> getAll() {
        List<EventTagDto> tags = tagService.findAll().stream()
                .map(EventTag::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTagDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventTagDto> save(@Valid @RequestBody EventTagRequestDto tagRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.save(tagRequestDto).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventTagDto> updateById(@Valid @RequestBody EventTagRequestDto tagRequestDto,
                                                  @PathVariable("id") Long id) {
        return ResponseEntity.ok(tagService.updateById(tagRequestDto, id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

