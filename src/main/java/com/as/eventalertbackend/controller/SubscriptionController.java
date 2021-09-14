package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.SubscriptionBody;
import com.as.eventalertbackend.data.model.Subscription;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService service;

    @Autowired
    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Subscription> find() {
        return ResponseEntity.ok(service.findByUserId(getPrincipalId()));
    }

    @PostMapping
    public ResponseEntity<Subscription> subscribe(@Valid @RequestBody SubscriptionBody body) {
        return ResponseEntity.ok(service.subscribe(body, getPrincipalId()));
    }

    @PutMapping
    public ResponseEntity<Subscription> update(@Valid @RequestBody SubscriptionBody body) {
        return ResponseEntity.ok(service.update(body, getPrincipalId()));
    }

    @DeleteMapping
    public void unsubscribe() {
        service.deleteByUserId(getPrincipalId());
    }

    private Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
