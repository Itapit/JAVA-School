package com.example.jwt_basics1.config;

public class JwtProperties {

    // Access Token expiration time (e.g., 15 minutes)
    public static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 minutes

    // Refresh Token expiration time (e.g., 7 days)
    public static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 days

    // The TOKEN_PREFIX constant is used to prefix the JWT in the Authorization header
    public static final String TOKEN_PREFIX = "Bearer ";

    // The HEADER_STRING constant is used to set the key of the Authorization header
    public static final String HEADER_STRING = "Authorization";

}
