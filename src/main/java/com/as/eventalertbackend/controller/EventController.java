package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventFilterRequest;
import com.as.eventalertbackend.dto.response.EventResponse;
import com.as.eventalertbackend.dto.response.PagedResponse;
import com.as.eventalertbackend.model.OrderCode;
import com.as.eventalertbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<PagedResponse<EventResponse>> getByFilter(@Valid @RequestBody EventFilterRequest filterRequest,
                                                                    @RequestParam("pageSize") int pageSize,
                                                                    @RequestParam("pageNumber") int pageNumber,
                                                                    @RequestParam(required = false, value = "orderCode") OrderCode orderCode) {
        return ResponseEntity.ok(eventService.findByFilter(filterRequest, pageSize, pageNumber, orderCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllByUserId(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(eventService.findAllByUserId(userId));
    }

    /*@PostMapping
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
    }*/

    @Secured({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
