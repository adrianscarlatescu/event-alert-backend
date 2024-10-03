package com.as.eventalertbackend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties("app")
public class AppProperties {

    private String name;
    private String imagesDirectoryPath;
    private Set<String> mockedUsersEmails;

    private Security security;
    private Notification notification;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class Security {
        private String secret;
        private Long accessTokenExpirationTime;
        private Long refreshTokenExpirationTime;
        private String accessTokenId;
        private String refreshTokenId;
        private String tokenPrefix;
        private String authHeader;
        private String authRegisterUrlRegex;
        private String authLoginUrlRegex;
        private String subscriptionTokenUrlRegex;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Notification {
        private Boolean enabled;
        private String firebaseServiceAccountPath;
        private String eventIdKey;
        private String eventDateTimeKey;
        private String eventTagNameKey;
        private String eventTagImagePathKey;
        private String eventSeverityNameKey;
        private String eventSeverityColorKey;
        private String eventLatitudeKey;
        private String eventLongitudeKey;
    }

}
