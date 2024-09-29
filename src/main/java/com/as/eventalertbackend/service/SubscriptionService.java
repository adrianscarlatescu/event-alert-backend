package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.SubscriptionRequest;
import com.as.eventalertbackend.dto.request.SubscriptionStatusRequest;
import com.as.eventalertbackend.dto.request.SubscriptionTokenRequest;
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

import java.util.List;

@Service
@Transactional
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

    public Subscription find(Long userId, String deviceId) {
        return subscriptionRepository.findByUserIdAndDeviceId(userId, deviceId)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND));
    }

    public Boolean subscriptionExists(Long userId, String deviceId) {
        return subscriptionRepository.existsByUserIdAndDeviceId(userId, deviceId);
    }

    public Subscription subscribe(SubscriptionRequest subscriptionRequest) {
        if (subscriptionExists(subscriptionRequest.getUserId(), subscriptionRequest.getDeviceId())) {
            throw new InvalidActionException(ApiErrorMessage.ALREADY_SUBSCRIBER);
        }

        User user = userService.findById(subscriptionRequest.getUserId());

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setDeviceId(subscriptionRequest.getDeviceId());
        subscription.setFirebaseToken(subscriptionRequest.getFirebaseToken());
        subscription.setLatitude(subscriptionRequest.getLatitude());
        subscription.setLongitude(subscriptionRequest.getLongitude());
        subscription.setRadius(subscriptionRequest.getRadius());
        subscription.setIsActive(true);

        return subscriptionRepository.save(subscription);
    }

    public Subscription update(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = find(subscriptionRequest.getUserId(), subscriptionRequest.getDeviceId());
        subscription.setLatitude(subscriptionRequest.getLatitude());
        subscription.setLongitude(subscriptionRequest.getLongitude());
        subscription.setRadius(subscriptionRequest.getRadius());
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateStatus(Long userId, String deviceToken, SubscriptionStatusRequest subscriptionStatusRequest) {
        Subscription subscription = find(userId, deviceToken);
        subscription.setIsActive(subscriptionStatusRequest.getIsActive());
        return subscriptionRepository.save(subscription);
    }


    public void updateToken(String deviceId, SubscriptionTokenRequest subscriptionTokenRequest) {
        log.info("Updating token for device");
        List<Subscription> subscriptions = subscriptionRepository.findAllByDeviceId(deviceId);

        if (!subscriptions.isEmpty()) {
            subscriptions.forEach(subscription -> subscription.setFirebaseToken(subscriptionTokenRequest.getFirebaseToken()));
            subscriptionRepository.saveAll(subscriptions);
            log.info("Tokens successfully updated");
        } else {
            log.info("No subscriptions eligible, skip update");
        }
    }

    public void delete(Long userId, String deviceId) {
        if (subscriptionExists(userId, deviceId)) {
            subscriptionRepository.deleteByUserIdAndDeviceId(userId, deviceId);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND);
        }
    }

}
