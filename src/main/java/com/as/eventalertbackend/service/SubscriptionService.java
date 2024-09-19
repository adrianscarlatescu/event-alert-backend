package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.SubscriptionRequest;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Subscription;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.reopsitory.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    public Subscription find(Long userId, String deviceToken) {
        return subscriptionRepository.findByUserIdAndDeviceToken(userId, deviceToken)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND));
    }

    public Subscription subscribe(Long userId, SubscriptionRequest subscriptionRequest) {
        if (subscriptionRepository.existsByUserIdAndDeviceToken(userId, subscriptionRequest.getDeviceToken())) {
            throw new InvalidActionException(ApiErrorMessage.ALREADY_SUBSCRIBER);
        }

        User user = userService.findById(userId);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setDeviceToken(subscriptionRequest.getDeviceToken());
        subscription.setLatitude(subscriptionRequest.getLatitude());
        subscription.setLongitude(subscriptionRequest.getLongitude());
        subscription.setRadius(subscriptionRequest.getRadius());

        return subscriptionRepository.save(subscription);
    }

    public Subscription update(Long userId, SubscriptionRequest subscriptionRequest) {
        Subscription subscription = find(userId, subscriptionRequest.getDeviceToken());
        subscription.setLatitude(subscriptionRequest.getLatitude());
        subscription.setLongitude(subscriptionRequest.getLongitude());
        subscription.setRadius(subscriptionRequest.getRadius());
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void delete(Long userId, String deviceToken) {
        if (subscriptionRepository.existsByUserIdAndDeviceToken(userId, deviceToken)) {
            subscriptionRepository.deleteByUserIdAndDeviceToken(userId, deviceToken);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND);
        }
    }

}
