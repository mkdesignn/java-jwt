package com.example.jwt

import org.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import spock.lang.Shared;
import spock.lang.Specification;

@SpringBootTest
class JwtApplicationTests extends Specification {

    @Shared
    def client = new RestTemplate()

    def 'should return 200 code when used valid credentials' () {

        when: 'login with invalid credentials'
        def personJsonObject = new JSONObject()
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString())
        def response = client.exchange("http://localhost:8080/login", HttpMethod.POST , request, String.class)

        then: 'server returns 200 code (ok)'
        assert response.getStatusCode().value() == 200 : 'response code should be 200 when tried to authenticate with valid credentials'
    }

}
