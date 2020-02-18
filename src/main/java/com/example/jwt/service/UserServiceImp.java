package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.exceptions.ExistentUsernameException;
import com.example.jwt.exceptions.IncorrectCredentialException;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.security.JwtUtil;
import com.example.jwt.transformer.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public ResponseEntity<TokenDTO> login(User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialException();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new TokenDTO(token));
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

}
