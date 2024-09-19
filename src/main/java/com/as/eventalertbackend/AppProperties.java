package com.as.eventalertbackend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties("app")
public class AppProperties {

    private Storage storage;
    private Security security;
    private Notification notification;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Storage {
        private String serverPath;
        private String imagesPath;
    }

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
        private String authRegisterUrl;
        private String authLoginUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Notification {
        private Boolean enabled;
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
