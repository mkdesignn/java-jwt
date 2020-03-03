package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;
import com.example.jwt.exceptions.IncorrectCredentialException;
import com.example.jwt.exceptions.RefreshTokenException;
import com.example.jwt.exceptions.UserNotExistsException;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.security.JwtUtil;
import com.example.jwt.transformer.BaseResponseDTO;
import com.example.jwt.transformer.TokenDTO;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public List<String> login(User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialException();
        }

        return generateToken(user);

    }

    public User registerUser(User user) throws ExistentUsernameException {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ExistentUsernameException();
        }

        return userRepository.save(
                User.builder()
                        .username(user.getUsername())
                        .name(user.getName())
                        .password(bCryptPasswordEncoder.encode(user.getPassword()))
                        .email(user.getEmail())
                        .build()
        );
    }

    public void delete(Long id) throws UserNotExistsException {
        if(userRepository.findById(id).isPresent()){
            userRepository.delete(userRepository.findById(id).get());
        } else {
            throw new UserNotExistsException();
        }
    }

    public List<String> refreshToken(String token) throws Exception {

        User user = userRepository.findByRefreshToken(token);
        if(user == null){
            throw new RefreshTokenException();
        }

        return generateToken(user);
    }

    private List<String> generateToken(User user){

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        User userByUsername = userRepository.findByUsername(user.getUsername());
        String randomString = RandomString.make(64);
        userByUsername.setRefreshToken(randomString);
        userRepository.save(userByUsername);

        List<String> list = new ArrayList<>();
        list.add(jwtUtil.generateToken(userDetails));
        list.add(randomString);

        return list;
    }
}
