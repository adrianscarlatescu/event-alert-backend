package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.SubscriptionRequest;
import com.as.eventalertbackend.dto.request.SubscriptionStatusRequest;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Subscription;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.reopsitory.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    public Subscription find(Long userId, String firebaseToken) {
        return subscriptionRepository.findByUserIdAndFirebaseToken(userId, firebaseToken)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND));
    }

    public Subscription subscribe(Long userId, SubscriptionRequest subscriptionRequest) {
        if (subscriptionRepository.existsByUserIdAndFirebaseToken(userId, subscriptionRequest.getFirebaseToken())) {
            throw new InvalidActionException(ApiErrorMessage.ALREADY_SUBSCRIBER);
        }

        User user = userService.findById(userId);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setFirebaseToken(subscriptionRequest.getFirebaseToken());
        subscription.setLatitude(subscriptionRequest.getLatitude());
        subscription.setLongitude(subscriptionRequest.getLongitude());
        subscription.setRadius(subscriptionRequest.getRadius());
        subscription.setIsActive(true);

        return subscriptionRepository.save(subscription);
    }

    public Subscription update(Long userId, SubscriptionRequest subscriptionRequest) {
        Subscription subscription = find(userId, subscriptionRequest.getFirebaseToken());
        subscription.setLatitude(subscriptionRequest.getLatitude());
        subscription.setLongitude(subscriptionRequest.getLongitude());
        subscription.setRadius(subscriptionRequest.getRadius());
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateStatus(Long userId, SubscriptionStatusRequest subscriptionStatusRequest) {
        Subscription subscription = find(userId, subscriptionStatusRequest.getFirebaseToken());
        subscription.setIsActive(subscriptionStatusRequest.getIsActive());
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void delete(Long userId, String firebaseToken) {
        if (subscriptionRepository.existsByUserIdAndFirebaseToken(userId, firebaseToken)) {
            subscriptionRepository.deleteByUserIdAndFirebaseToken(userId, firebaseToken);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND);
        }
    }

}
