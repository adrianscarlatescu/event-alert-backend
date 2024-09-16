package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventFilterRequestDto;
import com.as.eventalertbackend.dto.request.EventRequestDto;
import com.as.eventalertbackend.dto.response.EventResponseDto;
import com.as.eventalertbackend.dto.response.PageResponseDto;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/filter")
    public ResponseEntity<PageResponseDto<EventResponseDto>> getByFilter(@Valid @RequestBody EventFilterRequestDto filterDataDto,
                                                                         @RequestParam("pageSize") int pageSize,
                                                                         @RequestParam("pageNumber") int pageNumber,
                                                                         @RequestParam(required = false, value = "order") Order order) {
        return ResponseEntity.ok(eventService.findByFilter(filterDataDto, pageSize, pageNumber, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllByUserId(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(eventService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> save(@Valid @RequestBody EventRequestDto eventRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.save(eventRequestDto));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateById(@Valid @RequestBody EventRequestDto eventRequestDto,
                                                       @PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.updateById(eventRequestDto, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
