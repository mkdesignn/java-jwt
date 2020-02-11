package com.example.jwt.functional

import com.example.jwt.controller.AuthController
import com.example.jwt.data.DataProviderAuth
import com.example.jwt.entity.User
import com.example.jwt.exceptions.ExistentUsernameException
import com.example.jwt.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AuthSpec extends Specification {

    @Autowired
    private AuthController authController

    @Autowired
    private UserService userService

    private MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build()
    }

    def 'register should return 400 bad request when username has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutUsername()
        def response = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        def exception = response.andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage()
        assert exception.indexOf("username") > 0
    }

    def 'register should return 400 bad request when name has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutName()
        def response = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        def exception = response.andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage()
        assert exception.indexOf("name") > 0
    }

    def 'register should return 400 bad request when password has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutPassword()
        def response = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        def exception = response.andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage()
        assert exception.indexOf("password") > 0
    }

    def 'register should return 400 bad request when email has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutEmail()
        def response = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        def exception = response.andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage()
        assert exception.indexOf("email") > 0
    }

    def 'register should return 200 if all goes well'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithAllFields()
        def response = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        assert response.andExpect(status().isOk())
    }

    def 'register should return 400 bad request if Username has already been taken'() {

        when:
        userService.registerUser(
                User.builder()
                        .email("mohammad.kaab@gmail.com")
                        .name("mohammad")
                        .username("mohammad")
                        .password("mohammad")
                        .build()
        )

        def personJsonObject = DataProviderAuth.registerWithAllFields()
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        def exception = thrown(ExistentUsernameException)
        assert exception.getMessage().indexOf("Username has already been taken") == 0
    }

    def 'login should throw IncorrectCredentialException if password is incorrect'() {

        when:
        def personJsonObject = DataProviderAuth.loginWithWrongPassword()
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))


        then:
        def exception = thrown(Exception)
        assert exception.getMessage().indexOf("Incorrect username or password") >= 0
    }

    def 'login should throw IncorrectCredentialException if username is not exist'() {

        when:
        def personJsonObject = DataProviderAuth.loginWithNonexistentUsername()
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        def exception = thrown(Exception)
        assert exception.getMessage().indexOf("Incorrect username or password") >= 0
    }

    def 'login should return 200 if all goes well'() {

        when:
        def personJsonObject = DataProviderAuth.loginWithCorrectParams()
        def response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        println()
        assert response.andExpect(status().isOk())
    }
}
