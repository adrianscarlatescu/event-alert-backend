package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.SubscriptionRequestDto;
import com.as.eventalertbackend.dto.response.SubscriptionDto;
import com.as.eventalertbackend.jpa.entity.Subscription;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.mapper.Mapper;
import com.as.eventalertbackend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final Mapper<Subscription, SubscriptionDto> mapper;
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(Mapper<Subscription, SubscriptionDto> mapper,
                                  SubscriptionService subscriptionService) {
        this.mapper = mapper;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/{deviceToken}")
    public ResponseEntity<SubscriptionDto> find(@PathVariable("deviceToken") String deviceToken) {
        return ResponseEntity.ok(mapper.mapTo(subscriptionService.find(getPrincipalId(), deviceToken)));
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> subscribe(@Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        return ResponseEntity.ok(mapper.mapTo(subscriptionService.subscribe(getPrincipalId(), subscriptionRequestDto)));
    }

    @PutMapping
    public ResponseEntity<SubscriptionDto> update(@Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        return ResponseEntity.ok(mapper.mapTo(subscriptionService.update(getPrincipalId(), subscriptionRequestDto)));
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
