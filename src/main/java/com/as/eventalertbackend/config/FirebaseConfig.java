package com.as.eventalertbackend.config;

import com.as.eventalertbackend.AppProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private final AppProperties appProperties;

    @Autowired
    public FirebaseConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    @ConditionalOnProperty(value = "app.notification.enabled", havingValue = "true")
    public FirebaseMessaging firebaseMessaging() throws IOException {
        String serviceAccountPath = appProperties.getNotification().getFirebaseServiceAccountPath();
        String appName = appProperties.getName();

        ClassPathResource serviceAccountResource = new ClassPathResource(serviceAccountPath);

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccountResource.getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, appName);
        return FirebaseMessaging.getInstance(app);
    }

}
