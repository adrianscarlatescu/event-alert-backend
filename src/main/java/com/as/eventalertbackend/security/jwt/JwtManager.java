package com.as.eventalertbackend.security.jwt;

import com.as.eventalertbackend.AppProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class JwtManager {

    private final AppProperties appProperties;

    @Autowired
    public JwtManager(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String generateAccessToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setId(appProperties.getSecurity().getAccessTokenId())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + appProperties.getSecurity().getAccessTokenExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, appProperties.getSecurity().getSecret())
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setId(appProperties.getSecurity().getRefreshTokenId())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + appProperties.getSecurity().getRefreshTokenExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, appProperties.getSecurity().getSecret())
                .compact();
    }

    public boolean isAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(appProperties.getSecurity().getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getId()
                .equals(appProperties.getSecurity().getAccessTokenId());
    }

    public boolean isRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(appProperties.getSecurity().getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getId()
                .equals(appProperties.getSecurity().getRefreshTokenId());
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(appProperties.getSecurity().getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getSecurity().getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(appProperties.getSecurity().getAuthHeader());

        if (authHeader != null && authHeader.startsWith(appProperties.getSecurity().getTokenPrefix())) {
            if (authHeader.contains("\"")) {
                authHeader = authHeader.replaceAll("\"", "");
            }
            return authHeader.substring(appProperties.getSecurity().getTokenPrefix().length());
        }

        return null;
    }

}
