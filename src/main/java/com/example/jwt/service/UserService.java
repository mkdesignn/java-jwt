package com.example.jwt.service;

import com.example.jwt.entity.AppUser;
import com.example.jwt.exceptions.ExistentUsernameException;

public interface UserService {

    AppUser registerUser(AppUser user) throws ExistentUsernameException;
}
