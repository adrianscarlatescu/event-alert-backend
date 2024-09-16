package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.Event;
import com.as.eventalertbackend.jpa.entity.Subscription;
import com.as.eventalertbackend.jpa.reopsitory.EventRepository;
import com.as.eventalertbackend.jpa.reopsitory.SubscriptionRepository;
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

    private final SubscriptionRepository subscriptionRepository;
    private final EventRepository eventRepository;

    private FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(AppProperties appProperties,
                               SubscriptionRepository subscriptionRepository,
                               EventRepository eventRepository,
                               ApplicationContext applicationContext) {
        this.appProperties = appProperties;
        this.subscriptionRepository = subscriptionRepository;
        this.eventRepository = eventRepository;
        try {
            this.firebaseMessaging = applicationContext.getBean(FirebaseMessaging.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.warn("Firebase messaging is not enabled");
        }
    }

    public void send(Long eventId) {
        if (!appProperties.getNotification().getEnabled()) {
            return;
        }

        Event event = eventRepository.findById(eventId)
                        .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.EVENT_NOT_FOUND));

        log.info("Sending notifications for event: {}", event.getId());
        List<Subscription> subscriptions =
                subscriptionRepository.findByLocation(
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getUser().getId());

        Map<String, String> messageMap = getMessageMap(event);

        String severity = event.getSeverity().getName().toLowerCase();
        String tag = event.getTag().getName().toLowerCase();

        String title = "New " + severity + " " + tag + " reported!";
        String body = "Click the notification for more details.";

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        List<Message> messages = subscriptions.stream()
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

    private Map<String, String> getMessageMap(Event event) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(appProperties.getNotification().getEventIdKey(), String.valueOf(event.getId()));
        messageMap.put(appProperties.getNotification().getEventDateTimeKey(), event.getDateTime().toString());
        messageMap.put(appProperties.getNotification().getEventTagNameKey(), event.getTag().getName());
        messageMap.put(appProperties.getNotification().getEventTagImagePathKey(), event.getTag().getImagePath());
        messageMap.put(appProperties.getNotification().getEventSeverityNameKey(), event.getSeverity().getName());
        messageMap.put(appProperties.getNotification().getEventSeverityColorKey(), String.valueOf(event.getSeverity().getColor()));
        messageMap.put(appProperties.getNotification().getEventLatitudeKey(), String.valueOf(event.getLatitude()));
        messageMap.put(appProperties.getNotification().getEventLongitudeKey(), String.valueOf(event.getLongitude()));
        return messageMap;
    }

}
