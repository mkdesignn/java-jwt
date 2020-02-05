package com.example.jwt.transformer;


import com.example.jwt.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String name;

    private String email;

    public UserDTO(User user){

        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
    }

}
