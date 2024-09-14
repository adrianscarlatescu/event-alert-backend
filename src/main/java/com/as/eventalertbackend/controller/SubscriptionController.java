package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.SubscriptionRequestDto;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.dto.SubscriptionDto;
import com.as.eventalertbackend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<SubscriptionDto> find(@RequestParam("deviceToken") String deviceToken) {
        return ResponseEntity.ok(subscriptionService.find(getPrincipalId(), deviceToken).toDto());
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> subscribe(@Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        return ResponseEntity.ok(subscriptionService.subscribe(getPrincipalId(), subscriptionRequestDto).toDto());
    }

    @PutMapping
    public ResponseEntity<SubscriptionDto> update(@Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        return ResponseEntity.ok(subscriptionService.update(getPrincipalId(), subscriptionRequestDto).toDto());
    }

    @DeleteMapping
    public ResponseEntity<Void> unsubscribe(@RequestParam("deviceToken") String deviceToken) {
        subscriptionService.delete(getPrincipalId(), deviceToken);
        return ResponseEntity.ok().build();
    }

    private Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
