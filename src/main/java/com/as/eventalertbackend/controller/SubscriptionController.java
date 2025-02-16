package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.subscription.*;
import com.as.eventalertbackend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.HEAD, path = "/{userId}/{deviceId}")
    public ResponseEntity<Void> subscriptionExists(@PathVariable("userId") Long userId,
                                                   @PathVariable("deviceId") String deviceId) {
        return subscriptionService.subscriptionExists(userId, deviceId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/{deviceId}")
    public ResponseEntity<SubscriptionDTO> find(@PathVariable("userId") Long userId,
                                                @PathVariable("deviceId") String deviceId) {
        return ResponseEntity.ok(subscriptionService.find(userId, deviceId));
    }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> subscribe(@Valid @RequestBody SubscriptionCreateDTO subscriptionCreateDTO) {
        return ResponseEntity.ok(subscriptionService.subscribe(subscriptionCreateDTO));
    }

    @PutMapping(path = "/{userId}/{deviceId}")
    public ResponseEntity<SubscriptionDTO> update(@PathVariable("userId") Long userId,
                                                  @PathVariable("deviceId") String deviceId,
                                                  @Valid @RequestBody SubscriptionUpdateDTO subscriptionUpdateDTO) {
        return ResponseEntity.ok(subscriptionService.update(subscriptionUpdateDTO, userId, deviceId));
    }

    @PatchMapping(path = "/{userId}/{deviceId}/status")
    public ResponseEntity<SubscriptionDTO> updateStatus(@PathVariable("userId") Long userId,
                                                        @PathVariable("deviceId") String deviceId,
                                                        @Valid @RequestBody SubscriptionStatusDTO subscriptionStatusDTO) {
        return ResponseEntity.ok(subscriptionService.updateStatus(userId, deviceId, subscriptionStatusDTO));
    }

    @PatchMapping(path = "/{deviceId}/token")
    public ResponseEntity<Void> updateToken(@PathVariable("deviceId") String deviceId,
                                            @Valid @RequestBody SubscriptionTokenDTO subscriptionTokenDTO) {
        subscriptionService.updateToken(deviceId, subscriptionTokenDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/{deviceId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable("userId") Long userId,
                                            @PathVariable("deviceId") String deviceId) {
        subscriptionService.delete(userId, deviceId);
        return ResponseEntity.ok().build();
    }

}
