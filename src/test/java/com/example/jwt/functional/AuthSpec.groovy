package com.example.jwt.functional

import com.fasterxml.jackson.core.JsonParser
import jdk.nashorn.internal.parser.JSONParser
import org.json.JSONObject
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class AuthSpec extends Specification {

    @Shared
    def client = new RestTemplate()

    def 'register should return 400 bad request when username has not sent to it' () {

        when:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        def personJsonObject = new JSONObject()
        personJsonObject.put("name", "mohammad")
        personJsonObject.put("password", "mohammad")
        personJsonObject.put("email", "mohammad.kaab@gmail.com")
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers)

        client.exchange("http://localhost:8080/register", HttpMethod.POST , request, String.class)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("username") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 400 bad request when name has not sent to it' () {

        when:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        def personJsonObject = new JSONObject()
        personJsonObject.put("username", "mohammad")
        personJsonObject.put("password", "mohammad")
        personJsonObject.put("email", "mohammad.kaab@gmail.com")
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers)

        client.exchange("http://localhost:8080/register", HttpMethod.POST , request, String.class)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("name") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 400 bad request when password has not sent to it' () {

        when:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        def personJsonObject = new JSONObject()
        personJsonObject.put("username", "mohammad")
        personJsonObject.put("name", "mohammad")
        personJsonObject.put("email", "mohammad.kaab@gmail.com")
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers)

        client.exchange("http://localhost:8080/register", HttpMethod.POST , request, String.class)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("password") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 400 bad request when email has not sent to it' () {

        when:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        def personJsonObject = new JSONObject()
        personJsonObject.put("username", "mohammad")
        personJsonObject.put("name", "mohammad")
        personJsonObject.put("password", "mk")
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers)

        client.exchange("http://localhost:8080/register", HttpMethod.POST , request, String.class)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("email") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 200 if all goes well' () {

        when:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        def personJsonObject = new JSONObject()
        personJsonObject.put("username", "mohammad")
        personJsonObject.put("name", "mohammad")
        personJsonObject.put("password", "mk")
        personJsonObject.put("email", "mohammad.kaab@gmail.com")
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers)

        def response = client.exchange("http://localhost:8080/register", HttpMethod.POST , request, String.class)

        then:
        assert response.getStatusCode().value() == 200
    }
}
