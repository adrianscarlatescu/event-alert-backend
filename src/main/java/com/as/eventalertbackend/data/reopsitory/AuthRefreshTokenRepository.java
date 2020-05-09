package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.AuthRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRefreshTokenRepository extends JpaRepository<AuthRefreshToken, Long> {

    Boolean existsByRefreshToken(String refreshToken);

    void deleteByUserId(Long id);

}