package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.SubscriptionRequestDto;
import com.as.eventalertbackend.dto.response.SubscriptionDto;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.Subscription;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.reopsitory.SubscriptionRepository;
import com.as.eventalertbackend.jpa.reopsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public SubscriptionDto find(Long userId, String deviceToken) {
        return subscriptionRepository.findByUserIdAndDeviceToken(userId, deviceToken)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND))
                .toDto();
    }

    public SubscriptionDto subscribe(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        if (subscriptionRepository.existsByUserIdAndDeviceToken(userId, subscriptionRequestDto.getDeviceToken())) {
            throw new InvalidActionException(ApiErrorMessage.ALREADY_SUBSCRIBER);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setDeviceToken(subscriptionRequestDto.getDeviceToken());
        subscription.setLatitude(subscriptionRequestDto.getLatitude());
        subscription.setLongitude(subscriptionRequestDto.getLongitude());
        subscription.setRadius(subscriptionRequestDto.getRadius());
        return subscriptionRepository.save(subscription).toDto();
    }

    public SubscriptionDto update(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        Subscription subscription = subscriptionRepository.findByUserIdAndDeviceToken(userId, subscriptionRequestDto.getDeviceToken())
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND));
        subscription.setLatitude(subscriptionRequestDto.getLatitude());
        subscription.setLongitude(subscriptionRequestDto.getLongitude());
        subscription.setRadius(subscriptionRequestDto.getRadius());
        return subscriptionRepository.save(subscription).toDto();
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
