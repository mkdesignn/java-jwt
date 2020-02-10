package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;

public interface UserService {

    User registerUser(User user) throws ExistentUsernameException;
}
