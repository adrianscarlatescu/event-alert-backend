package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Range(min = AppConstants.MIN_RADIUS, max = AppConstants.MAX_RADIUS)
    private Integer radius;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private String firebaseToken;

    @Column(nullable = false)
    private Boolean isActive;

}
