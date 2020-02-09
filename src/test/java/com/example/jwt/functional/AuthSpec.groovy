package com.example.jwt.functional

import com.example.jwt.controller.AuthController
import com.example.jwt.data.DataProviderAuth
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
}
