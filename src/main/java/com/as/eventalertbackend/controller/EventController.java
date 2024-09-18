package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.EventFilterRequestDto;
import com.as.eventalertbackend.dto.request.EventRequestDto;
import com.as.eventalertbackend.dto.response.EventDto;
import com.as.eventalertbackend.dto.response.PageDto;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.jpa.entity.Event;
import com.as.eventalertbackend.mapper.Mapper;
import com.as.eventalertbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final Mapper<Event, EventDto> mapper;
    private final EventService eventService;

    @Autowired
    public EventController(Mapper<Event, EventDto> mapper,
                           EventService eventService) {
        this.mapper = mapper;
        this.eventService = eventService;
    }

    @PostMapping("/filter")
    public ResponseEntity<PageDto<EventDto>> getByFilter(@Valid @RequestBody EventFilterRequestDto filterDataDto,
                                                         @RequestParam("pageSize") int pageSize,
                                                         @RequestParam("pageNumber") int pageNumber,
                                                         @RequestParam(required = false, value = "order") Order order) {
        Page<Event> eventsPage = eventService.findByFilter(filterDataDto, pageSize, pageNumber, order);
        return ResponseEntity.ok(
                new PageDto<>(
                        eventsPage.getTotalPages(),
                        eventsPage.getTotalElements(),
                        mapper.mapTo(eventsPage.getContent())
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.mapTo(eventService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllByUserId(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(mapper.mapTo(eventService.findAllByUserId(userId)));
    }

    @PostMapping
    public ResponseEntity<EventDto> save(@Valid @RequestBody EventRequestDto eventRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.mapTo(eventService.save(eventRequestDto)));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateById(@Valid @RequestBody EventRequestDto eventRequestDto,
                                               @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.mapTo(eventService.updateById(eventRequestDto, id)));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
