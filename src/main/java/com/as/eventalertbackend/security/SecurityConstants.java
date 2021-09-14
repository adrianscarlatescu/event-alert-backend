package com.as.eventalertbackend.security;

public abstract class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenerateJWTs";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 100 * 1_200_000L; // 20 minutes
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 2_592_000_000L; // 30 days
    public static final String ACCESS_TOKEN_ID = "AccessTokenId";
    public static final String REFRESH_TOKEN_ID = "RefreshTokenId";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTH_REGISTER_URL = "/auth/register";
    public static final String AUTH_LOGIN_URL = "/auth/login";
    public static final String AUTH_REFRESH_TOKEN_URL = "/auth/refresh";

}
