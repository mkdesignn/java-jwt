package com.example.jwt.exceptions;

import javax.security.auth.message.AuthException;

public class IncorrectCredentialException extends AuthException {
    @Override
    public String getMessage() {
        return "Incorrect username or password";
    }
}
