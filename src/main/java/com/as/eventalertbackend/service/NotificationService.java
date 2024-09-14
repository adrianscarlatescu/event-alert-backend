package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.data.model.Event;
import com.as.eventalertbackend.data.model.Subscription;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationService {
    
    private final AppProperties appProperties;
    private final SubscriptionService subscriptionService;
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(AppProperties appProperties,
                               SubscriptionService subscriptionService,
                               ApplicationContext applicationContext) {
        this.appProperties = appProperties;
        this.subscriptionService = subscriptionService;
        try {
            this.firebaseMessaging = applicationContext.getBean(FirebaseMessaging.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.warn("Firebase messaging is not enabled");
        }
    }

    public void send(Event newEvent) {
        log.info("Sending notifications for event: {}", newEvent.getId());
        List<Subscription> subscriptions = subscriptionService.findByLocation(newEvent.getLatitude(), newEvent.getLongitude());

        Map<String, String> messageMap = getMessageMap(newEvent);

        String severity = newEvent.getSeverity().getName().toLowerCase();
        String tag = newEvent.getTag().getName().toLowerCase();

        String title = "New " + severity + " " + tag + " reported!";
        String body = "Click the notification for more details.";

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        List<Message> messages = subscriptions.stream()
                .filter(subscription -> subscription.getUser().getId().longValue() != newEvent.getUser().getId().longValue())
                .map(subscription -> Message.builder()
                        .setToken(subscription.getDeviceToken())
                        .setNotification(notification)
                        .putAllData(messageMap)
                        .build())
                .collect(Collectors.toList());

        if (messages.isEmpty()) {
            log.info("Messages list is empty");
            return;
        }

        try {
            BatchResponse batchResponse = firebaseMessaging.sendAll(messages);
            log.info("Notifications sent, success: {}, fail: {}", batchResponse.getSuccessCount(), batchResponse.getFailureCount());
            batchResponse.getResponses().forEach(response -> {
                if (!response.isSuccessful()) {
                    log.error("Could not send notification", response.getException());
                } else {
                    log.info("Notification sent, identifier: {}", response.getMessageId());
                }
            });
        } catch (FirebaseMessagingException e) {
            log.error("Could not send notifications", e);
        }
    }

    private Map<String, String> getMessageMap(Event newEvent) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(appProperties.getNotification().getEventIdKey(), String.valueOf(newEvent.getId()));
        messageMap.put(appProperties.getNotification().getEventDateTimeKey(), newEvent.getDateTime().toString());
        messageMap.put(appProperties.getNotification().getEventTagNameKey(), newEvent.getTag().getName());
        messageMap.put(appProperties.getNotification().getEventTagImagePathKey(), newEvent.getTag().getImagePath());
        messageMap.put(appProperties.getNotification().getEventSeverityNameKey(), newEvent.getSeverity().getName());
        messageMap.put(appProperties.getNotification().getEventSeverityColorKey(), String.valueOf(newEvent.getSeverity().getColor()));
        messageMap.put(appProperties.getNotification().getEventLatitudeKey(), String.valueOf(newEvent.getLatitude()));
        messageMap.put(appProperties.getNotification().getEventLongitudeKey(), String.valueOf(newEvent.getLongitude()));
        return messageMap;
    }

}
