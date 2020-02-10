package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(User user) {

        return userRepository.save(
                User.builder()
                        .username(user.getName())
                        .name(user.getName())
                        .password(user.getPassword())
                        .email(user.getEmail()).build()
        );
    }

}
