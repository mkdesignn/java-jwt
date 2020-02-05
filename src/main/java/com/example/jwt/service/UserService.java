package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class UserService implements UserServiceImp {

    private final UserRepository userRepository;

    public User RegisterUser(User user){

        return userRepository.save(user);
    }

}
