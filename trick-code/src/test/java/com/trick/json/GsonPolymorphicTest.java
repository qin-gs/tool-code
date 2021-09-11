package com.trick.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("gson 多态处理")
public class GsonPolymorphicTest {

    @Test
    public void test() {

        RuntimeTypeAdapterFactory<Animal> factory = RuntimeTypeAdapterFactory.<Animal>of(Animal.class, "type")
                .registerSubtype(Dog.class, "dog")
                .registerSubtype(Cat.class, "cat");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapterFactory(factory)
                .create();

        String s = "[\n" +
                "  {\n" +
                "    \"name\": \"a dog\",\n" +
                "    \"isDog\": true,\n" +
                "    \"type\": \"dog\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"a cat\",\n" +
                "    \"leg\": 4,\n" +
                "    \"type\": \"cat\"\n" +
                "  }\n" +
                "]";
        Object o = gson.fromJson(s, new TypeToken<List<Animal>>() {
        }.getType());
        System.out.println(o);

    }

}

class Animal {
    private String name;
}

class Dog extends Animal {
    private boolean isDog;
}

class Cat extends Animal {
    private boolean isCat;
    private int leg;
}