package com.as.eventalertbackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AuthLoginRequest implements Serializable {

    @Email(message = "Invalid email")
    @NotBlank(message = "The email is mandatory")
    private String email;

    @NotBlank(message = "The password is mandatory")
    private String password;

}
