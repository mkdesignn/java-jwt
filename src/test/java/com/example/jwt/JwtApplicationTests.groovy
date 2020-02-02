package com.example.jwt;

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared;
import spock.lang.Specification;

@SpringBootTest
class JwtApplicationTests extends Specification {

    @Shared
    def client = new RESTClient( "$SERVER_URL:$SERVER_PORT")

    def 'should return 200 code when used valid credentials' () {
        when: 'login with invalid credentials'
        def response = client.get( path : '/login' )

        then: 'server returns 200 code (ok)'
        assert response.status == 200 : 'response code should be 200 when tried to authenticate with valid credentials'
    }

}
