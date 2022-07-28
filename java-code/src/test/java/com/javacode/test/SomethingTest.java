package com.javacode.test;

import java.util.HashMap;
import java.util.Map;

public class SomethingTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("a", null);

        String s = map.get(null);
        System.out.println("s = " + s);
    }
}
