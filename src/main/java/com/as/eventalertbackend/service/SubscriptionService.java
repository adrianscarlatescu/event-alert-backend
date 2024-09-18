package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.SubscriptionRequestDto;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.Subscription;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.reopsitory.SubscriptionRepository;
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

    public Subscription subscribe(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        if (subscriptionRepository.existsByUserIdAndDeviceToken(userId, subscriptionRequestDto.getDeviceToken())) {
            throw new InvalidActionException(ApiErrorMessage.ALREADY_SUBSCRIBER);
        }

        User user = userService.findById(userId);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setDeviceToken(subscriptionRequestDto.getDeviceToken());
        subscription.setLatitude(subscriptionRequestDto.getLatitude());
        subscription.setLongitude(subscriptionRequestDto.getLongitude());
        subscription.setRadius(subscriptionRequestDto.getRadius());

        return subscriptionRepository.save(subscription);
    }

    public Subscription update(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        Subscription subscription = find(userId, subscriptionRequestDto.getDeviceToken());
        subscription.setLatitude(subscriptionRequestDto.getLatitude());
        subscription.setLongitude(subscriptionRequestDto.getLongitude());
        subscription.setRadius(subscriptionRequestDto.getRadius());
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
