package com.as.eventalertbackend.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = jwtUtils.parseJwt(request);

            boolean isNull = token == null;
            boolean isAccessToken = false;
            boolean isValid = false;

            if (isNull) {
                log.error("Null token, request URI: {}", request.getRequestURI());
            } else {
                isAccessToken = jwtUtils.isAccessToken(token);
                if (!isAccessToken) {
                    log.error("Invalid access token signature: {}, request URI: {}", token, request.getRequestURI());
                }

                isValid = jwtUtils.validateJwtToken(token);
                if (!isValid) {
                    log.error("Invalid token: {}, request URI: {}", token, request.getRequestURI());
                }
            }

            if (!isNull && isAccessToken && isValid) {
                String email = jwtUtils.getEmailFromJwtToken(token);
                UserDetails userDetails = userService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

}
