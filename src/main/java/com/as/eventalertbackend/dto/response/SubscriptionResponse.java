package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionResponse  implements Serializable {

    private Long id;
    private UserBaseResponse user;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private String deviceToken;

}
