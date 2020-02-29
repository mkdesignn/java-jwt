package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;
import com.example.jwt.exceptions.UserNotExistsException;
import com.example.jwt.transformer.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<TokenDTO> login(User user) throws Exception;

    User registerUser(User user) throws ExistentUsernameException;

    void delete(Long id) throws UserNotExistsException;
}
