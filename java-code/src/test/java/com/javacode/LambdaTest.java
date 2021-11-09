package com.javacode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

@DisplayName("lambda 测试")
public class LambdaTest {

    @Test
    public void test() {
        Function<Integer, Function<Integer, Function<Integer, Integer>>> func = x -> y -> z -> x + y + z;
        System.out.println(func.apply(2).apply(3).apply(4));

        Function<Integer, Function<Integer, Function<Integer, Integer>>> f = func;
        int[] data = {3, 4, 5};
        for (int d : data) {
            if (f instanceof Function) {
                Object apply = f.apply(d);
                if (apply instanceof Function) {
                    f = (Function) apply;
                } else {
                    System.out.println("结果: " + apply);
                }
            }

        }
    }
}
