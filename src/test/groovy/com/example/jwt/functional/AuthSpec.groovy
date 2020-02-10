package com.example.jwt.functional

import com.example.jwt.controller.AuthController
import com.example.jwt.data.DataProviderAuth
import com.example.jwt.entity.User
import com.example.jwt.exceptions.ExistentUsernameException
import com.example.jwt.repository.UserRepository
import com.example.jwt.service.UserService
import com.example.jwt.service.UserServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.util.NestedServletException
import spock.lang.Specification

import javax.security.auth.login.LoginContext
import javax.security.auth.message.AuthException

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

    def setup(){
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
//
//    def 'login should return 403 if password is incorrect'() {
//
//        when:
//        def personJsonObject = DataProviderAuth.loginWithWrongPassword()
//        def response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))
//
//
//        then:
//        assert response.andExpect(status().isForbidden())
//    }
//
//    def 'login should return 403 if username is not exist'() {
//
//        when:
//        def personJsonObject = DataProviderAuth.loginWithNonexistentUsername()
//        def response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))
//
//        then:
//        assert response.andExpect(status().isForbidden())
//    }
//
//    def 'login should return 200 if all goes well'() {
//
//        when:
//        def personJsonObject = DataProviderAuth.loginWithCorrectParams()
//        def response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))
//
//        then:
//        assert response.andExpect(status().isOk())
//    }

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
        assert exception.getMessage().indexOf("Username")
    }
}
