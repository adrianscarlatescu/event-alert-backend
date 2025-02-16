package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.subscription.*;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.Subscription;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.reopsitory.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class SubscriptionService {

    private final ModelMapper mapper;

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    @Autowired
    public SubscriptionService(ModelMapper mapper,
                               SubscriptionRepository subscriptionRepository,
                               UserService userService) {
        this.mapper = mapper;
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    Subscription findEntity(Long userId, String deviceId) {
        return subscriptionRepository.findByUserIdAndDeviceId(userId, deviceId)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.SUBSCRIPTION_NOT_FOUND));
    }

    public SubscriptionDTO find(Long userId, String deviceId) {
        return mapper.map(findEntity(userId, deviceId), SubscriptionDTO.class);
    }

    public Boolean subscriptionExists(Long userId, String deviceId) {
        return subscriptionRepository.existsByUserIdAndDeviceId(userId, deviceId);
    }

    public SubscriptionDTO subscribe(SubscriptionCreateDTO subscriptionCreateDTO) {
        if (subscriptionExists(subscriptionCreateDTO.getUserId(), subscriptionCreateDTO.getDeviceId())) {
            throw new InvalidActionException(ApiErrorMessage.ALREADY_SUBSCRIBER);
        }

        User user = userService.findEntityById(subscriptionCreateDTO.getUserId());

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setDeviceId(subscriptionCreateDTO.getDeviceId());
        subscription.setFirebaseToken(subscriptionCreateDTO.getFirebaseToken());
        subscription.setLatitude(subscriptionCreateDTO.getLatitude());
        subscription.setLongitude(subscriptionCreateDTO.getLongitude());
        subscription.setRadius(subscriptionCreateDTO.getRadius());
        subscription.setIsActive(true);

        return mapper.map(subscriptionRepository.save(subscription), SubscriptionDTO.class);
    }

    public SubscriptionDTO update(SubscriptionUpdateDTO subscriptionUpdateDTO, Long userId, String deviceId) {
        Subscription subscription = findEntity(userId, deviceId);

        if (subscriptionUpdateDTO.getFirebaseToken() != null) {
            subscription.setFirebaseToken(subscriptionUpdateDTO.getFirebaseToken());
        }
        if (subscriptionUpdateDTO.getLatitude() != null) {
            subscription.setLatitude(subscriptionUpdateDTO.getLatitude());
        }
        if (subscriptionUpdateDTO.getLongitude() != null) {
            subscription.setLongitude(subscriptionUpdateDTO.getLongitude());
        }
        if (subscriptionUpdateDTO.getRadius() != null) {
            subscription.setRadius(subscriptionUpdateDTO.getRadius());
        }

        return mapper.map(subscription, SubscriptionDTO.class);
    }

    public SubscriptionDTO updateStatus(Long userId, String deviceToken, SubscriptionStatusDTO subscriptionStatusDTO) {
        Subscription subscription = findEntity(userId, deviceToken);
        subscription.setIsActive(subscriptionStatusDTO.getIsActive());
        return mapper.map(subscription, SubscriptionDTO.class);
    }


    public void updateToken(String deviceId, SubscriptionTokenDTO subscriptionTokenDTO) {
        log.info("Updating token for device");
        List<Subscription> subscriptions = subscriptionRepository.findAllByDeviceId(deviceId);

        if (!subscriptions.isEmpty()) {
            subscriptions.forEach(subscription -> subscription.setFirebaseToken(subscriptionTokenDTO.getFirebaseToken()));
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
