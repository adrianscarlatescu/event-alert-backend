package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.EventBody;
import com.as.eventalertbackend.controller.request.EventFilterBody;
import com.as.eventalertbackend.controller.response.PagedResponse;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.enums.Order;
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

    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/filter")
    public PagedResponse<Event> getByFilter(@RequestBody EventFilterBody body,
                                            @RequestParam("pageSize") int pageSize,
                                            @RequestParam("pageNumber") int pageNumber,
                                            @RequestParam(required = false, value = "order") Order order) {
        return service.findByFilter(body, pageSize, pageNumber, order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public List<Event> getByUserId(@RequestParam("userId") Long userId) {
        return service.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Event> save(@Valid @RequestBody EventBody body) {
        return ResponseEntity.ok(service.save(body));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateById(@Valid @RequestBody EventBody body, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.updateById(body, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

}