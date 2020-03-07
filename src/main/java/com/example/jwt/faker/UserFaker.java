package com.example.jwt.faker;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFaker implements FakerInterface<User> {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Nullable
    private String username;

    @Nullable
    private String password;

    public User create(){

        Faker faker = new Faker();

        return userRepository.save(User.builder()
                .refreshToken(RandomString.make(64))
                .email(faker.bothify("????##@gmail.com"))
                .username(username == null ? faker.bothify("????????#######") : username)
                .password(password == null ? bCryptPasswordEncoder.encode(faker.bothify("????????#######")) : bCryptPasswordEncoder.encode(password))
                .name(faker.name().firstName())
                .build());

    }

    public User make(){

        Faker faker = new Faker();

        return User.builder()
                .refreshToken(RandomString.make(64))
                .email(faker.bothify("????##@gmail.com"))
                .username(username.isEmpty() ? faker.bothify("????????#######") : username)
                .password(password.isEmpty() ? faker.bothify("????????#######") : password)
                .name(faker.name().firstName())
                .build();
    }

    public UserFaker setUsername(String username){

        this.username = username;

        return this;
    }

    public UserFaker setPassword(String password){

        this.password = password;

        return this;
    }

}
