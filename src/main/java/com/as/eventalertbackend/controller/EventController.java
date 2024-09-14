package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventFilterRequestDto;
import com.as.eventalertbackend.controller.request.EventRequestDto;
import com.as.eventalertbackend.controller.response.PagedResponseDto;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.dto.EventDto;
import com.as.eventalertbackend.enums.Order;
import com.as.eventalertbackend.service.EventService;
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

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/filter")
    public ResponseEntity<PagedResponseDto<EventDto>> getByFilter(@Valid @RequestBody EventFilterRequestDto filterRequestDto,
                                                                  @RequestParam("pageSize") int pageSize,
                                                                  @RequestParam("pageNumber") int pageNumber,
                                                                  @RequestParam(required = false, value = "order") Order order) {
        Page<Event> eventsPage = eventService.findByFilter(filterRequestDto, pageSize, pageNumber, order);
        return ResponseEntity.ok(
                new PagedResponseDto<>(
                        eventsPage.getTotalPages(),
                        eventsPage.getTotalElements(),
                        eventsPage.getContent().stream()
                                .map(Event::toDto)
                                .collect(Collectors.toList())
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id).toDto());
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllByUserId(@RequestParam("userId") Long userId) {
        List<EventDto> events = eventService.findAllByUserId(userId).stream()
                .map(Event::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventDto> save(@Valid @RequestBody EventRequestDto eventRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.save(eventRequestDto).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateById(@Valid @RequestBody EventRequestDto eventRequestDto,
                                               @PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.updateById(eventRequestDto, id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
