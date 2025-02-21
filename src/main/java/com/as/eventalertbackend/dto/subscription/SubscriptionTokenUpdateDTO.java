package com.as.eventalertbackend.dto.subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionTokenUpdateDTO implements Serializable {

    @NotBlank(message = "The Firebase token is mandatory")
    private String firebaseToken;

}
