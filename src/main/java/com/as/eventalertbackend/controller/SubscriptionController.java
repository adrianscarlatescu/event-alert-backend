package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.SubscriptionRequest;
import com.as.eventalertbackend.dto.request.SubscriptionStatusRequest;
import com.as.eventalertbackend.dto.response.SubscriptionResponse;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.service.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final ModelMapper mapper;
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(ModelMapper mapper,
                                  SubscriptionService subscriptionService) {
        this.mapper = mapper;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/{firebaseToken}")
    public ResponseEntity<SubscriptionResponse> find(@PathVariable("firebaseToken") String firebaseToken) {
        return ResponseEntity.ok(mapper.map(subscriptionService.find(getPrincipalId(), firebaseToken), SubscriptionResponse.class));
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.ok(mapper.map(subscriptionService.subscribe(getPrincipalId(), subscriptionRequest), SubscriptionResponse.class));
    }

    @PutMapping
    public ResponseEntity<SubscriptionResponse> update(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.ok(mapper.map(subscriptionService.update(getPrincipalId(), subscriptionRequest), SubscriptionResponse.class));
    }

    @PatchMapping
    public ResponseEntity<SubscriptionResponse> updateStatus(@Valid @RequestBody SubscriptionStatusRequest subscriptionStatusRequest) {
        return ResponseEntity.ok(mapper.map(subscriptionService.updateStatus(getPrincipalId(), subscriptionStatusRequest), SubscriptionResponse.class));
    }

    @DeleteMapping("/{firebaseToken}")
    public ResponseEntity<Void> unsubscribe(@PathVariable("firebaseToken") String firebaseToken) {
        subscriptionService.delete(getPrincipalId(), firebaseToken);
        return ResponseEntity.ok().build();
    }

    private Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
