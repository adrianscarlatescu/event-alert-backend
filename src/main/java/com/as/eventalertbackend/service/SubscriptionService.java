package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.SubscriptionBody;
import com.as.eventalertbackend.data.model.Subscription;
import com.as.eventalertbackend.data.reopsitory.SubscriptionRepository;
import com.as.eventalertbackend.handler.exception.IllegalActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository repository;
    private final UserService userService;

    @Autowired
    public SubscriptionService(SubscriptionRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Subscription> findAll() {
        return repository.findAll();
    }

    public Subscription find(Long userId, String deviceToken) {
        return repository.findByUserIdAndDeviceToken(userId, deviceToken)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No subscription record for user " + userId + " with token " + deviceToken,
                        "Subscription not found"));
    }

    public Subscription subscribe(Long userId, SubscriptionBody body) {
        if (repository.existsByUserIdAndDeviceToken(userId, body.getDeviceToken())) {
            throw new IllegalActionException(
                    "Subscription found for user " + userId + " with token " + body.getDeviceToken(),
                    "Already subscriber");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(userService.findById(userId));
        subscription.setDeviceToken(body.getDeviceToken());
        subscription.setLatitude(body.getLatitude());
        subscription.setLongitude(body.getLongitude());
        subscription.setRadius(body.getRadius());
        return save(subscription);
    }

    public Subscription update(Long userId, SubscriptionBody body) {
        Subscription subscription = find(userId, body.getDeviceToken());
        subscription.setLatitude(body.getLatitude());
        subscription.setLongitude(body.getLongitude());
        subscription.setRadius(body.getRadius());
        return save(subscription);
    }

    public Subscription save(Subscription subscription) {
        return repository.save(subscription);
    }

    @Transactional
    public void delete(Long userId, String deviceToken) {
        if (repository.existsByUserIdAndDeviceToken(userId, deviceToken)) {
            repository.deleteByUserIdAndDeviceToken(userId, deviceToken);
        } else {
            throw new RecordNotFoundException(
                    "No subscription record for user " + userId + " with token " + deviceToken,
                    "Subscription not found");
        }
    }

    public List<Subscription> findByLocation(double eventLatitude, double eventLongitude) {
        return repository.findByLocation(eventLatitude, eventLongitude);
    }

}
