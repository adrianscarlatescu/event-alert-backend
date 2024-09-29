package com.as.eventalertbackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionStatusRequest {

    @NotNull(message = "The active status is mandatory")
    private Boolean isActive;

}
