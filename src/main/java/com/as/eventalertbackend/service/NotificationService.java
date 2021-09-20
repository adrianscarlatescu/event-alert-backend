package com.as.eventalertbackend.service;

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

    private static final String EVENT_ID_KEY = "eventId";
    private static final String EVENT_DATE_TIME_KEY = "eventDateTime";
    private static final String EVENT_TAG_NAME_KEY = "eventTagName";
    private static final String EVENT_TAG_IMAGE_PATH_KEY = "eventTagImagePath";
    private static final String EVENT_SEVERITY_NAME_KEY = "eventSeverityName";
    private static final String EVENT_SEVERITY_COLOR_KEY = "eventSeverityColor";
    private static final String EVENT_LATITUDE_KEY = "eventLatitude";
    private static final String EVENT_LONGITUDE_KEY = "eventLongitude";

    private final SubscriptionService subscriptionService;
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(SubscriptionService subscriptionService,
                               ApplicationContext applicationContext) {
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

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(EVENT_ID_KEY, String.valueOf(newEvent.getId()));
        messageMap.put(EVENT_DATE_TIME_KEY, newEvent.getDateTime().toString());
        messageMap.put(EVENT_TAG_NAME_KEY, newEvent.getTag().getName());
        messageMap.put(EVENT_TAG_IMAGE_PATH_KEY, newEvent.getTag().getImagePath());
        messageMap.put(EVENT_SEVERITY_NAME_KEY, newEvent.getSeverity().getName());
        messageMap.put(EVENT_SEVERITY_COLOR_KEY, String.valueOf(newEvent.getSeverity().getColor()));
        messageMap.put(EVENT_LATITUDE_KEY, String.valueOf(newEvent.getLatitude()));
        messageMap.put(EVENT_LONGITUDE_KEY, String.valueOf(newEvent.getLongitude()));

        String title = "New " + newEvent.getSeverity().getName().toLowerCase() + " " + newEvent.getTag().getName().toLowerCase() + " reported!";
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

        try {
            BatchResponse batchResponse = firebaseMessaging.sendAll(messages);
            log.info("Notifications sent, success: {}, fail: {}", batchResponse.getSuccessCount(), batchResponse.getFailureCount());
            batchResponse.getResponses().forEach(sendResponse -> {
                if (!sendResponse.isSuccessful()) {
                    log.error("Could not send notification, reason: {}", sendResponse.getException());
                } else {
                    log.info("Notification sent, identifier: {}", sendResponse.getMessageId());
                }
            });
        } catch (FirebaseMessagingException e) {
            log.error("Could not send notifications, reason: {}", e);
        }
    }

}
