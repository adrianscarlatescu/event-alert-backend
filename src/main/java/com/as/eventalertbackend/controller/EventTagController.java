package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventTagRequestDto;
import com.as.eventalertbackend.dto.response.EventTagDto;
import com.as.eventalertbackend.jpa.entity.EventTag;
import com.as.eventalertbackend.mapper.Mapper;
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

    private final Mapper<EventTag, EventTagDto> mapper;
    private final EventTagService tagService;

    @Autowired
    public EventTagController(Mapper<EventTag, EventTagDto> mapper,
                              EventTagService tagService) {
        this.mapper = mapper;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<EventTagDto>> getAll() {
        return ResponseEntity.ok(mapper.mapTo(tagService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTagDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.mapTo(tagService.findById(id)));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventTagDto> save(@Valid @RequestBody EventTagRequestDto tagRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapTo(tagService.save(tagRequestDto)));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventTagDto> updateById(@Valid @RequestBody EventTagRequestDto tagRequestDto,
                                                  @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.mapTo(tagService.updateById(tagRequestDto, id)));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

