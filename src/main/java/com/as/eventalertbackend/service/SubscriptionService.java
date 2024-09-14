package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.SubscriptionRequestDto;
import com.as.eventalertbackend.data.model.Subscription;
import com.as.eventalertbackend.data.reopsitory.SubscriptionRepository;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    public Subscription find(Long userId, String deviceToken) {
        return subscriptionRepository.findByUserIdAndDeviceToken(userId, deviceToken)
                .orElseThrow(() -> new RecordNotFoundException("Subscription not found"));
    }

    public Subscription subscribe(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        if (subscriptionRepository.existsByUserIdAndDeviceToken(userId, subscriptionRequestDto.getDeviceToken())) {
            throw new InvalidActionException("Already subscriber");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(userService.findById(userId));
        subscription.setDeviceToken(subscriptionRequestDto.getDeviceToken());
        subscription.setLatitude(subscriptionRequestDto.getLatitude());
        subscription.setLongitude(subscriptionRequestDto.getLongitude());
        subscription.setRadius(subscriptionRequestDto.getRadius());
        return save(subscription);
    }

    public Subscription update(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        Subscription subscription = find(userId, subscriptionRequestDto.getDeviceToken());
        subscription.setLatitude(subscriptionRequestDto.getLatitude());
        subscription.setLongitude(subscriptionRequestDto.getLongitude());
        subscription.setRadius(subscriptionRequestDto.getRadius());
        return save(subscription);
    }

    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void delete(Long userId, String deviceToken) {
        if (subscriptionRepository.existsByUserIdAndDeviceToken(userId, deviceToken)) {
            subscriptionRepository.deleteByUserIdAndDeviceToken(userId, deviceToken);
        } else {
            throw new RecordNotFoundException("Subscription not found");
        }
    }

    public List<Subscription> findByLocation(double eventLatitude, double eventLongitude) {
        return subscriptionRepository.findByLocation(eventLatitude, eventLongitude);
    }

}
