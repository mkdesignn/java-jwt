package com.example.jwt.controller;

import com.example.jwt.repository.UserRepository;
import com.example.jwt.transformer.DTO;
import com.example.jwt.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping(path = "login")
    public long login(){
        return userRepository.count();
    }

    @PostMapping(path = "register")
    public DTO<UserTransformer> register(){

        return new DTO<>();
    }
}
