package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;
import com.example.jwt.service.UserService;
import com.example.jwt.transformer.BaseResponseDTO;
import com.example.jwt.transformer.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity login(@RequestBody User user) throws Exception {
        return userService.login(user);
    }

    @PostMapping(path = "/register")
    public BaseResponseDTO register(@Valid @RequestBody User user) throws ExistentUsernameException {

        ModelMapper modelMapper = new ModelMapper();
        UserDTO userTransformer = modelMapper.map(userService.registerUser(user), UserDTO.class);
        return new BaseResponseDTO<>(userTransformer, HttpStatus.OK.value());
    }
}
