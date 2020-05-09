package com.as.eventalertbackend.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
public class AuthRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;
    @OneToOne
    private User user;

    public AuthRefreshToken(String refreshToken, User user) {
        this.refreshToken = refreshToken;
        this.user = user;
    }

}
