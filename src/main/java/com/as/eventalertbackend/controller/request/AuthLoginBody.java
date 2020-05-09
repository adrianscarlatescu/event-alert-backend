package com.as.eventalertbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthLoginBody {

    @Email(message = "Invalid email")
    @NotEmpty(message = "The email is mandatory")
    private String email;

    @NotEmpty(message = "The password is mandatory")
    private String password;

}
