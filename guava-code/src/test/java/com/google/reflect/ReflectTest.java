package com.google.reflect;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ReflectTest {

    @Test
    public void test() {
        TypeToken<String> stringTypeToken = TypeToken.of(String.class);

        TypeToken<List<String>> token = new TypeToken<>() {
        };
    }
}
