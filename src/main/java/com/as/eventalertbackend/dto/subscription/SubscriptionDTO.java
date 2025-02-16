package com.as.eventalertbackend.dto.subscription;

import com.as.eventalertbackend.dto.user.UserBaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionDTO implements Serializable {

    private Long id;
    private UserBaseDTO user;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String deviceId;
    private String firebaseToken;
    private Boolean isActive;

}
