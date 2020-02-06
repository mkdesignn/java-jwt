package com.example.jwt.functional

import com.example.jwt.data.DataProviderAuth
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class AuthSpec extends Specification {

    @Shared
    def client = new RestTemplate()

    def 'register should return 400 bad request when username has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutUsername()
        DataProviderAuth.exchange(client, "http://localhost:8080/register", HttpMethod.POST, personJsonObject)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("username") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 400 bad request when name has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutName()
        DataProviderAuth.exchange(client, "http://localhost:8080/register", HttpMethod.POST, personJsonObject)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("name") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 400 bad request when password has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutPassword()
        DataProviderAuth.exchange(client, "http://localhost:8080/register", HttpMethod.POST, personJsonObject)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("password") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 400 bad request when email has not sent to it'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithoutEmail()
        DataProviderAuth.exchange(client, "http://localhost:8080/register", HttpMethod.POST, personJsonObject)

        then:
        HttpClientErrorException ex = thrown()
        assert ex.getMessage().indexOf("email") > 0
        assert ex.getStatusCode().value() == 400
    }

    def 'register should return 200 if all goes well'() {

        when:
        def personJsonObject = DataProviderAuth.registerWithAllFields()
        def response = DataProviderAuth.exchange(client, "http://localhost:8080/register", HttpMethod.POST, personJsonObject)

        then:
        assert response.getStatusCode().value() == 200
    }
}
