package com.example.jwt.transformer;


import com.example.jwt.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String name;

    private String email;

}
