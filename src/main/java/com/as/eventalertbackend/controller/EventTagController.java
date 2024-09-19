package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventTagRequest;
import com.as.eventalertbackend.dto.response.EventTagResponse;
import com.as.eventalertbackend.service.EventTagService;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper mapper;
    private final EventTagService tagService;

    @Autowired
    public EventTagController(ModelMapper mapper,
                              EventTagService tagService) {
        this.mapper = mapper;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<EventTagResponse>> getAll() {
        return ResponseEntity.ok(
                tagService.findAll().stream()
                        .map(tag -> mapper.map(tag, EventTagResponse.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTagResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(tagService.findById(id), EventTagResponse.class));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<EventTagResponse> save(@Valid @RequestBody EventTagRequest tagRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(tagService.save(tagRequest), EventTagResponse.class));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventTagResponse> updateById(@Valid @RequestBody EventTagRequest tagRequest,
                                                       @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(tagService.updateById(tagRequest, id), EventTagResponse.class));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

