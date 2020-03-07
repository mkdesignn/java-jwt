package com.example.jwt.functional

import com.example.jwt.controller.UserController
import com.example.jwt.faker.UserFaker
import com.example.jwt.repository.UserRepository
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
class UserSpec extends Specification{

    @Autowired
    UserController userController

    @Autowired
    UserRepository userRepository

    @Autowired
    UserFaker userFaker

    def "remove should return no content if all goes well"()
    {

        given:
        def mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
        def user = userFaker.setUsername("mohammad").setPassword("mohammad").create()
        def parser = new JSONParser()

        when:
        def response = mockMvc.perform(delete("/user/"+user.id))

        then:
        response.andExpect(status().isOk())

        JSONObject json = (JSONObject) parser.parse(response.andReturn().getResponse().getContentAsString())
        assert json.status == 204

    }
}
