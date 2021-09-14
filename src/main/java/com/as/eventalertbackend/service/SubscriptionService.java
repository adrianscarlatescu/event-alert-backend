package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.SubscriptionBody;
import com.as.eventalertbackend.data.model.Subscription;
import com.as.eventalertbackend.data.reopsitory.SubscriptionRepository;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Subscription findByUserId(Long id) {
        return repository.findByUserId(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No subscription record for user " + id,
                        "Subscription not found"));
    }

    public Subscription subscribe(SubscriptionBody body, Long userId) {
        Subscription subscription = new Subscription();
        subscription.setUser(userService.findById(userId));
        subscription.setLatitude(body.getLatitude());
        subscription.setLongitude(body.getLongitude());
        subscription.setRadius(body.getRadius());
        return save(subscription);
    }

    public Subscription update(SubscriptionBody body, Long userId) {
        Subscription subscription = findByUserId(userId);
        subscription.setLatitude(body.getLatitude());
        subscription.setLongitude(body.getLongitude());
        subscription.setRadius(body.getRadius());
        return save(subscription);
    }

    public Subscription save(Subscription subscription) {
        return repository.save(subscription);
    }

    public void deleteByUserId(Long id) {
        if (repository.existsByUserId(id)) {
            repository.deleteByUserId(id);
        } else {
            throw new RecordNotFoundException(
                    "No subscription record for user " + id,
                    "Subscription not found");
        }
    }

    public List<Long> findUsersIdsByFilter(double eventLatitude, double eventLongitude, long excludedUserId) {
        return repository.findUsersIdsByFilter(eventLatitude, eventLongitude, excludedUserId);
    }

}
