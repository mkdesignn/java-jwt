package com.example.jwt.exceptions;

import javax.security.auth.message.AuthException;

public class UserNotExistsException extends AuthException {
    @Override
    public String getMessage() {
        return "User Not exists";
    }
}
