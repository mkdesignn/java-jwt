package com.example.jwt.faker;


public interface FakerInterface<T> {

    T create();

    T make();
}
