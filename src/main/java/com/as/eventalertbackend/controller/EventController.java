package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventFilterRequest;
import com.as.eventalertbackend.dto.request.EventRequest;
import com.as.eventalertbackend.dto.response.EventResponse;
import com.as.eventalertbackend.dto.response.PageResponse;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    private final ModelMapper mapper;
    private final EventService eventService;

    @Autowired
    public EventController(ModelMapper mapper,
                           EventService eventService) {
        this.mapper = mapper;
        this.eventService = eventService;
    }

    @PostMapping("/filter")
    public ResponseEntity<PageResponse<EventResponse>> getByFilter(@Valid @RequestBody EventFilterRequest filterRequest,
                                                                   @RequestParam("pageSize") int pageSize,
                                                                   @RequestParam("pageNumber") int pageNumber,
                                                                   @RequestParam(required = false, value = "order") Order order) {
        Page<Event> eventsPage = eventService.findByFilter(filterRequest, pageSize, pageNumber, order);
        return ResponseEntity.ok(
                new PageResponse<>(
                        eventsPage.getTotalPages(),
                        eventsPage.getTotalElements(),
                        eventsPage.getContent().stream()
                                .map(event -> mapper.map(event, EventResponse.class))
                                .collect(Collectors.toList())
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(eventService.findById(id), EventResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllByUserId(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(
                eventService.findAllByUserId(userId).stream()
                        .map(event -> mapper.map(event, EventResponse.class))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<EventResponse> save(@Valid @RequestBody EventRequest eventRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(eventService.save(eventRequest), EventResponse.class));
    }

    @Secured({"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateById(@Valid @RequestBody EventRequest eventRequest,
                                                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(eventService.updateById(eventRequest, id), EventResponse.class));
    }

    @Secured({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
