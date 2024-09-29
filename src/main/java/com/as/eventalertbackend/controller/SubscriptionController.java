package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.SubscriptionRequest;
import com.as.eventalertbackend.dto.request.SubscriptionStatusRequest;
import com.as.eventalertbackend.dto.request.SubscriptionTokenRequest;
import com.as.eventalertbackend.dto.response.SubscriptionResponse;
import com.as.eventalertbackend.service.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.HEAD, path = "/{userId}/{deviceId}")
    public ResponseEntity<Void> subscriptionExists(@PathVariable("userId") Long userId,
                                                   @PathVariable("deviceId") String deviceId) {
        return subscriptionService.subscriptionExists(userId, deviceId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/{deviceId}")
    public ResponseEntity<SubscriptionResponse> find(@PathVariable("userId") Long userId,
                                                     @PathVariable("deviceId") String deviceId) {
        return ResponseEntity.ok(mapper.map(subscriptionService.find(userId, deviceId), SubscriptionResponse.class));
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.ok(mapper.map(subscriptionService.subscribe(subscriptionRequest), SubscriptionResponse.class));
    }

    @PutMapping
    public ResponseEntity<SubscriptionResponse> update(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.ok(mapper.map(subscriptionService.update(subscriptionRequest), SubscriptionResponse.class));
    }

    @PatchMapping(path = "/{userId}/{deviceId}/status")
    public ResponseEntity<SubscriptionResponse> updateStatus(@PathVariable("userId") Long userId,
                                                             @PathVariable("deviceId") String deviceId,
                                                             @Valid @RequestBody SubscriptionStatusRequest subscriptionStatusRequest) {
        return ResponseEntity.ok(mapper.map(subscriptionService.updateStatus(userId, deviceId, subscriptionStatusRequest), SubscriptionResponse.class));
    }

    @PatchMapping(path = "/{deviceId}/tokens")
    public ResponseEntity<Void> updateTokens(@PathVariable("deviceId") String deviceId,
                                             @Valid @RequestBody SubscriptionTokenRequest subscriptionTokenRequest) {
        subscriptionService.updateTokens(deviceId, subscriptionTokenRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/{deviceId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable("userId") Long userId,
                                            @PathVariable("deviceId") String deviceId) {
        subscriptionService.delete(userId, deviceId);
        return ResponseEntity.ok().build();
    }

}
