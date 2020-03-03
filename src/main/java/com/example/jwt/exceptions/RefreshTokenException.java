package com.example.jwt.exceptions;

import javax.security.auth.message.AuthException;

public class RefreshTokenException extends AuthException {
    @Override
    public String getMessage() {
        return "Unauthorized to do the request";
    }
}
