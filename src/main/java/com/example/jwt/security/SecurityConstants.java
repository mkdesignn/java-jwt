package com.example.jwt.security;

public class SecurityConstants {

    public static final String SECRET = "9283kaab2892384238amin93823942imany";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/register";

}
