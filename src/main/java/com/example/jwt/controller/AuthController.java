package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.UserService;
import com.example.jwt.transformer.BaseResponseDTO;
import com.example.jwt.transformer.UseCaseErrorDTO;
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

    @PostMapping(path = "register")
    public BaseResponseDTO register(@Valid @RequestBody User user) {

        ModelMapper modelMapper = new ModelMapper();
        UserDTO userTransformer = modelMapper.map(userService.registerUser(user), UserDTO.class);
        return new BaseResponseDTO<>(userTransformer, HttpStatus.OK.value());
    }
}
