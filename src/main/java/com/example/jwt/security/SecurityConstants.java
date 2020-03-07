package com.example.jwt.security;

public class SecurityConstants {

    public static final String SECRET = "9283kaab2892384238amin93823942imany";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 600; // 10 min
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 15552000; // 6 month
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SIGN_UP_URL = "/register";
    public static final String LOGIN_URL = "/login";
    public static final String REFRESH_URL = "/refresh";

}
