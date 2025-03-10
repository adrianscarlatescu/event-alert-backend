package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.event.EventCreateDTO;
import com.as.eventalertbackend.dto.event.EventDTO;
import com.as.eventalertbackend.dto.event.EventUpdateDTO;
import com.as.eventalertbackend.dto.event.EventsFilterDTO;
import com.as.eventalertbackend.dto.page.PageDTO;
import com.as.eventalertbackend.enums.EventsOrder;
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
    public ResponseEntity<PageDTO<EventDTO>> getByFilter(@Valid @RequestBody EventsFilterDTO eventsFilterDTO,
                                                         @RequestParam("pageSize") int pageSize,
                                                         @RequestParam("pageNumber") int pageNumber,
                                                         @RequestParam(value = "order") EventsOrder eventsOrder) {
        return ResponseEntity.ok(eventService.findByFilter(eventsFilterDTO, pageSize, pageNumber, eventsOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllByUserId(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(eventService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<EventDTO> save(@Valid @RequestBody EventCreateDTO eventCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.save(eventCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateById(@Valid @RequestBody EventUpdateDTO eventUpdateDTO,
                                               @PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.updateById(eventUpdateDTO, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
