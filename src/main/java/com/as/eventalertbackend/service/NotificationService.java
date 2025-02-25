package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.persistence.entity.Subscription;
import com.as.eventalertbackend.persistence.reopsitory.SubscriptionRepository;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class NotificationService {

    private static final String KEY_EVENT_ID = "eventId";
    private static final String KEY_CREATED_AT = "createdAt";
    private static final String KEY_TYPE_LABEL = "typeLabel";
    private static final String KEY_TYPE_IMAGE_PATH = "typeImagePath";
    private static final String KEY_SEVERITY_LABEL = "severityLabel";
    private static final String KEY_SEVERITY_COLOR = "severityColor";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    private final AppProperties appProperties;

    private final SubscriptionRepository subscriptionRepository;

    private FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(AppProperties appProperties,
                               SubscriptionRepository subscriptionRepository,
                               ApplicationContext applicationContext) {
        this.appProperties = appProperties;
        this.subscriptionRepository = subscriptionRepository;
        try {
            this.firebaseMessaging = applicationContext.getBean(FirebaseMessaging.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.warn("Firebase messaging is not enabled");
        }
    }

    public void send(Event event) {
        if (!appProperties.getNotification().getEnabled()) {
            return;
        }

        log.info("Sending notifications for event: {}", event.getId());
        List<Subscription> subscriptions =
                subscriptionRepository.findByLocation(
                        event.getLatitude(),
                        event.getLongitude(),
                        event.getUser().getId());

        Map<String, String> messageMap = getMessageMap(event);

        String severity = event.getSeverity().getLabel().toLowerCase();
        String type = event.getType().getLabel().toLowerCase();

        String title = "New " + severity + " " + type + " reported!";
        String body = "Click the notification for more details";

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        List<Message> messages = subscriptions.stream()
                .map(subscription -> Message.builder()
                        .setToken(subscription.getFirebaseToken())
                        .setNotification(notification)
                        .putAllData(messageMap)
                        .build())
                .collect(Collectors.toList());

        if (messages.isEmpty()) {
            log.info("Messages list is empty");
            return;
        }

        try {
            BatchResponse batchResponse = firebaseMessaging.sendEach(messages);
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
        messageMap.put(KEY_EVENT_ID, String.valueOf(event.getId()));
        messageMap.put(KEY_CREATED_AT, event.getCreatedAt().toString());
        messageMap.put(KEY_TYPE_LABEL, event.getType().getLabel());
        messageMap.put(KEY_TYPE_IMAGE_PATH, event.getType().getImagePath());
        messageMap.put(KEY_SEVERITY_LABEL, event.getSeverity().getLabel());
        messageMap.put(KEY_SEVERITY_COLOR, String.valueOf(event.getSeverity().getColor()));
        messageMap.put(KEY_LATITUDE, String.valueOf(event.getLatitude()));
        messageMap.put(KEY_LONGITUDE, String.valueOf(event.getLongitude()));
        return messageMap;
    }

}
