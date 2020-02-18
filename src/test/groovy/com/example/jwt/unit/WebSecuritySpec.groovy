package com.example.jwt.unit

import com.example.jwt.controller.AuthController
import com.example.jwt.exceptions.CustomRestExceptionHandler
import com.example.jwt.security.WebSecurity
import net.minidev.json.parser.JSONParser
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static com.example.jwt.security.SecurityConstants.LOGIN_URL
import static com.example.jwt.security.SecurityConstants.SIGN_UP_URL
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@AutoConfigureMockMvc
class WebSecuritySpec extends Specification {


}
