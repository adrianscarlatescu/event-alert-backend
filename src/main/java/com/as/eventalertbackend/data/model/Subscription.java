package com.as.eventalertbackend.data.model;

import com.as.eventalertbackend.dto.SubscriptionDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String deviceToken;

    public SubscriptionDto toDto() {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(getId());
        dto.setUser(getUser() == null ? null : getUser().toDto());
        dto.setLatitude(getLatitude());
        dto.setLongitude(getLongitude());
        dto.setRadius(getRadius());
        dto.setDeviceToken(getDeviceToken());
        return dto;
    }

}
