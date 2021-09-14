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

    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> subscribe(@Valid @RequestBody SubscriptionBody body) {
        User principal =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.subscribe(body, principal.getId()));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Subscription> update(@Valid @RequestBody SubscriptionBody body, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(service.update(body, userId));
    }

    @DeleteMapping("/unsubscribe/{userId}")
    public void unsubscribe(@PathVariable("userId") Long userId) {
        service.deleteByUserId(userId);
    }

}
