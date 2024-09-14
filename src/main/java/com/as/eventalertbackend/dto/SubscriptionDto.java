package com.as.eventalertbackend.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SubscriptionDto {

    private Long id;
    private UserDto user;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String deviceToken;

}
