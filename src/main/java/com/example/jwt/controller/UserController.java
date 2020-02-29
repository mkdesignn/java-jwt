package com.example.jwt.controller;

import com.example.jwt.service.UserService;
import com.example.jwt.transformer.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping(path = "/user/{id}")
    public BaseResponseDTO delete(@PathVariable Long id) throws Exception {
        userService.delete(id);

        return new BaseResponseDTO<>(null, HttpStatus.NO_CONTENT.value());
    }

}
