package com.example.jwt.service;

import com.example.jwt.entity.AppUser;
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

    public AppUser registerUser(AppUser user) throws ExistentUsernameException {

        AppUser checkingUser = userRepository.findByUsername(user.getUsername());

        if (checkingUser != null) {
            throw new ExistentUsernameException();
        }

        AppUser u = new AppUser();
        u.setName(user.getName());
        u.setUsername(user.getUsername());
        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        u.setEmail(user.getEmail());
        return userRepository.save(u);
    }

}
