package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;
import com.example.jwt.service.UserService;
import com.example.jwt.transformer.BaseResponseDTO;
import com.example.jwt.transformer.TokenDTO;
import com.example.jwt.transformer.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(path = "/login")
    public BaseResponseDTO login(@RequestBody User user) throws Exception {

        List<String> list = userService.login(user);

        return new BaseResponseDTO<>(
                TokenDTO.builder()
                        .token(list.get(0))
                        .refresh_token(list.get(1))
                        .build(), HttpStatus.OK.value());

    }

    @PostMapping(path = "/register")
    public BaseResponseDTO register(@Valid @RequestBody User user) throws ExistentUsernameException {

        ModelMapper modelMapper = new ModelMapper();
        UserDTO userTransformer = modelMapper.map(userService.registerUser(user), UserDTO.class);
        return new BaseResponseDTO<>(userTransformer, HttpStatus.OK.value());
    }
}
