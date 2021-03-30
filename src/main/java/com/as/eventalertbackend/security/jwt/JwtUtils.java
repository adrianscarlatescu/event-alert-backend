package com.as.eventalertbackend.security.jwt;

import com.as.eventalertbackend.security.SecurityConstants;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    public String generateAccessToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setId(SecurityConstants.ACCESS_TOKEN_ID)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + SecurityConstants.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setId(SecurityConstants.REFRESH_TOKEN_ID)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean isAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getId()
                .equals(SecurityConstants.ACCESS_TOKEN_ID);
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(authToken);
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
        String headerAuth = request.getHeader(SecurityConstants.HEADER_STRING);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            if (headerAuth.contains("\"")) {
                headerAuth = headerAuth.replaceAll("\"", "");
            }
            return headerAuth.substring(SecurityConstants.TOKEN_PREFIX.length());
        }

        return null;
    }

}
