package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.UserService;
import com.example.jwt.transformer.DTO;
import com.example.jwt.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userRepository;

    @PostMapping(path = "login")
    public long login(){
        //return userRepository.count();
        return 123;
    }

    @PostMapping(path = "register")
    public User register(@Valid @RequestBody User user){

        return userRepository.RegisterUser(user);
    }
}
