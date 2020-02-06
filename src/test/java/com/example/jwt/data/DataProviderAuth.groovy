package com.example.jwt.data

import org.json.JSONObject
import org.springframework.http.*
import org.springframework.web.client.RestTemplate

class DataProviderAuth {

    static JSONObject registerWithoutUsername() {
        def object = new JSONObject()
        object = attachName(object)
        object = attachPassword(object)
        object = attachEmail(object)
        return object
    }


    static JSONObject registerWithoutName() {
        def object = new JSONObject()
        object = attachUsername(object)
        object = attachPassword(object)
        object = attachEmail(object)
        return object
    }


    static JSONObject registerWithoutPassword() {
        def object = new JSONObject()
        object = attachUsername(object)
        object = attachName(object)
        object = attachEmail(object)
        return object
    }


    static JSONObject registerWithoutEmail() {
        def object = new JSONObject()
        object = attachUsername(object)
        object = attachName(object)
        object = attachPassword(object)
        return object
    }


    static JSONObject registerWithAllFields() {
        def object = new JSONObject()
        object = attachUsername(object)
        object = attachName(object)
        object = attachPassword(object)
        object = attachEmail(object)
        return object
    }

    private static JSONObject attachName(JSONObject object) {
        object.put("name", "mohammad")
        return object
    }

    private static JSONObject attachUsername(JSONObject object) {
        object.put("username", "mohammad")
        return object
    }

    private static JSONObject attachPassword(JSONObject object) {
        object.put("password", "mohammad")
        return object
    }

    private static JSONObject attachEmail(JSONObject object) {
        object.put("email", "mohammad.kaab@gmail.com")
        return object
    }

    static HttpEntity<String> requestOf(JSONObject object) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        return new HttpEntity<String>(object.toString(), headers)
    }

    static ResponseEntity exchange(RestTemplate client, String url, HttpMethod method, JSONObject object) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        HttpEntity request = new HttpEntity<String>(object.toString(), headers)
        return client.exchange(url, method, request, String.class)
    }

}
