package com.as.eventalertbackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionStatusRequest {

    @NotBlank(message = "The Firebase token is mandatory")
    private String firebaseToken;

    @NotNull(message = "The active status is mandatory")
    private Boolean isActive;

}
