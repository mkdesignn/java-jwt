package com.example.jwt.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Setter
@Getter
@Builder
@Table(name = "users")
public class User implements Serializable {

    @Id
    private Long id;

    @NotEmpty(message = "Name field should not be empty")
    private String name;

    @NotEmpty(message = "Username field should not be empty")
    @Column(name = "username", length = 65)
    private String username;

    @NotEmpty(message = "Password field should not be empty")
    @Column(name = "password", length = 120)
    private String password;

    @NotEmpty(message = "Email field should not be empty")
    @Email
    @Column(name = "email", unique = true)
    private String email;
}
