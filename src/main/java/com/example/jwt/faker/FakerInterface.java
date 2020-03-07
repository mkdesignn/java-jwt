package com.example.jwt.faker;


import java.util.List;
import java.util.Optional;

public interface FakerInterface<T> {

    T create();

    T make();
}
