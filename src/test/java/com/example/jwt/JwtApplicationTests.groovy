package com.example.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc;
import spock.lang.Specification;

@SpringBootTest
class JwtApplicationTests extends Specification {


    def "writing first tests with groovy using spock"() {
        expect: 'mohammad' == 'mohammad'
    }

}
