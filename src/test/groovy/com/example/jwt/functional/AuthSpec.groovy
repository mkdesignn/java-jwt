package com.example.jwt.functional

import com.example.jwt.controller.AuthController
import com.example.jwt.data.DataProviderAuth
import com.example.jwt.entity.User
import com.example.jwt.faker.UserFaker
import com.example.jwt.service.UserService
import net.minidev.json.parser.JSONParser
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import javax.servlet.ServletException

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
class AuthSpec extends Specification {

    @Autowired
    private AuthController authController

    @Autowired
    private UserService userService

    private MockMvc mockMvc

    private JSONParser parser

    @Autowired
    private UserFaker userFaker

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build()
        parser = new JSONParser()
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

        given:
        userFaker.setUsername("mohammad").create()

        when:
        def personJsonObject = DataProviderAuth.registerWithAllFields()
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        def exception = thrown(ServletException)
        assert exception.getMessage().indexOf("Username has already been taken") >= 0

                //.getMessage().indexOf("Username has already been taken") == 0
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

        given:
        userFaker.setUsername("mohammad").setPassword("mohammad").create()

        when:
        def personJsonObject = DataProviderAuth.loginWithCorrectParams()
        def response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))

        then:
        println()
        assert response.andExpect(status().isOk())
    }

    def 'login should return an expected response'() {

        given:
        userFaker.setUsername("mohammad").setPassword("mohammad").create()
        def personJsonObject = DataProviderAuth.loginWithCorrectParams()

        when:
        def response = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(personJsonObject.toString()))
                .andReturn().getResponse().getContentAsString()

        then:
        JSONParser parser = new JSONParser()
        JSONObject json = (JSONObject) parser.parse(response)

        assert json.data.token != null
        assert json.data.refresh_token != null
    }

    def 'refresh should throw an error if it did not send in the request'() {

        when:
        def response = mockMvc.perform(get("/refresh"))

        then:
        assert response.andReturn().getResolvedException().getMessage().indexOf("refresh_token") >= 0
    }

    def 'refresh should throw an error if refresh_token was not found in database'() {

        when:
        mockMvc.perform(get("/refresh?refresh_token=123"))

        then:
        def exception = thrown(Exception)
        assert exception.getMessage().indexOf("Unauthorized to do the request") >= 0
    }

    def 'refresh should return expected response'() {

        given:
        User user = userFaker.create()

        when:
        def response = mockMvc.perform(get("/refresh?refresh_token="+user.getRefreshToken()))
                .andReturn().getResponse().getContentAsString()

        then:
        JSONParser parser = new JSONParser()
        JSONObject json = (JSONObject) parser.parse(response)

        assert json.data.token != null
        assert json.data.refresh_token != null
    }
}
