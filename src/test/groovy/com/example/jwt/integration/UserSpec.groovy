package com.example.jwt.integration

import com.example.jwt.controller.UserController
import com.example.jwt.faker.UserFaker
import com.example.jwt.repository.UserRepository
import com.example.jwt.service.UserServiceImp
import net.minidev.json.parser.JSONParser
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
class UserSpec extends Specification {

    @Autowired
    UserController userController

    @Autowired
    UserRepository userRepository

    @Autowired
    UserFaker userFaker

    def "user should not be found in database if delete endpoint get called"(){

        given:
        def mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
        def user = userFaker.create()

        when:
        mockMvc.perform(delete("/user/"+user.id))

        then:
        assert userRepository.findByUsername(user.getUsername()) == null

    }
}
