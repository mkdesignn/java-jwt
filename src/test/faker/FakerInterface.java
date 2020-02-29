package com.example.jwt.faker;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

public interface FakerInterface<T> {

    T create();

    T make();
}
