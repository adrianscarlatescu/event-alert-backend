package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionResponseDto {

    private Long id;
    private UserResponseDto user;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String deviceToken;

}
