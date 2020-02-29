package com.example.jwt.functional

import com.example.jwt.controller.UserController
import com.example.jwt.entity.User
import com.example.jwt.service.UserServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

@SpringBootTest
@AutoConfigureMockMvc
class UserSpec extends Specification{

    @Autowired
    UserController userController

    @Autowired
    UserServiceImp userServiceImp

    def "remove should throw an error if user not found"()
    {

        given:
        def mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
        User user = User.builder().name("test").
        userServiceImp.registerUser()

        when:
        mockMvc.perform(delete("/user/"))

    }
}
