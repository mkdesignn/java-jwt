package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import com.example.jwt.transformer.BaseResponseDTO;
import com.example.jwt.transformer.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(path = "login")
    public long login(){
        //return userRepository.count();
        return 123;
    }

    @PostMapping(path = "register")
    public BaseResponseDTO<UserDTO> register(@Valid @RequestBody User user){

        UserDTO userTransformer = new UserDTO(userService.RegisterUser(user));

        return new BaseResponseDTO<>(userTransformer, HttpStatus.OK.value());
    }
}
