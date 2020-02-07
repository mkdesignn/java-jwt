package com.example.jwt.exceptions;

import javax.security.auth.message.AuthException;

public class ExistentUsernameException extends AuthException {
    @Override
    public String getMessage() {
        return "Username has already been taken";
    }
}
