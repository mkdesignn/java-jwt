package com.example.jwt.faker;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFaker implements FakerInterface<User> {

    private final UserRepository userRepository;

    public User create(){

        Faker faker = new Faker();

        return userRepository.save(User.builder()
                .email(faker.bothify("????##@gmail.com"))
                .username(faker.bothify("????????#######"))
                .password(faker.bothify("????????#######"))
                .name(faker.name().firstName())
                .build());
    }

    public User make(){

        Faker faker = new Faker();

        return User.builder()
                .email(faker.bothify("????##@gmail.com"))
                .username(faker.bothify("????????#######"))
                .password(faker.bothify("????????#######"))
                .name(faker.name().firstName())
                .build();
    }

}
