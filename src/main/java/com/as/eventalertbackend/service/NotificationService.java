package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationService {

    private final SubscriptionService subscriptionService;

    @Autowired
    public NotificationService(UserService userService,
                               SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }


    public void send(Event newEvent) {
        log.info("Sending notifications for event: {}", newEvent.getId());
        List<Long> usersIds = subscriptionService.findUsersIdsByFilter(
                newEvent.getLatitude(), newEvent.getLongitude(), newEvent.getUser().getId());

        log.info("Send notifications to: {}", usersIds);
    }

}
