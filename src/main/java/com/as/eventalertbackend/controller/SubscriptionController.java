package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.SubscriptionRequestDto;
import com.as.eventalertbackend.dto.response.SubscriptionResponseDto;
import com.as.eventalertbackend.jpa.entity.User;
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

    @GetMapping("/{deviceToken}")
    public ResponseEntity<SubscriptionResponseDto> find(@PathVariable("deviceToken") String deviceToken) {
        return ResponseEntity.ok(subscriptionService.find(getPrincipalId(), deviceToken));
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> subscribe(@Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        return ResponseEntity.ok(subscriptionService.subscribe(getPrincipalId(), subscriptionRequestDto));
    }

    @PutMapping
    public ResponseEntity<SubscriptionResponseDto> update(@Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        return ResponseEntity.ok(subscriptionService.update(getPrincipalId(), subscriptionRequestDto));
    }

    @DeleteMapping("/{deviceToken}")
    public ResponseEntity<Void> unsubscribe(@PathVariable("deviceToken") String deviceToken) {
        subscriptionService.delete(getPrincipalId(), deviceToken);
        return ResponseEntity.ok().build();
    }

    private Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
