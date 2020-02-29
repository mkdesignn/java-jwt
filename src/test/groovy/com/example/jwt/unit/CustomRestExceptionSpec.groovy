package com.example.jwt.unit

import com.example.jwt.controller.AuthController
import com.example.jwt.exceptions.CustomRestExceptionHandler
import net.minidev.json.parser.JSONParser
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@AutoConfigureMockMvc
class CustomRestExceptionSpec extends Specification {

    @Autowired
    AuthController authController

    def "CustomRestExceptionHandler should return the expected response"(){

        def mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build()

        when:
        def response = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")).andReturn().getResponse().getContentAsString()

        then:
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response)
        assert json.data.errors != null
        assert json.data.errors.length() == 4
        assert json.toString().contains("password")
        assert json.toString().contains("username")
        assert json.toString().contains("email")
        assert json.toString().contains("name")
    }
}
