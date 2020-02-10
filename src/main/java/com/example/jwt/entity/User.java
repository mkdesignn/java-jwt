package com.example.jwt.entity;


import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Builder
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}, name = "username_unique")})
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name field should not be empty")
    private String name;

    @NotEmpty(message = "Username field should not be empty")
    @Column(name = "username", length = 32, unique = true)
    private String username;

    @NotEmpty(message = "Password field should not be empty")
    @Column(name = "password", length = 120)
    private String password;

    @NotEmpty(message = "Email field should not be empty")
    @Email
    @Column(name = "email", unique = true)
    private String email;
}
