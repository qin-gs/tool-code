package com.javacode.feature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("jdk14 instanceof新特性")
public class InstanceofTest {

    @Test
    public void test(Object o) {
        // 之前的操作
        // 一个测试（obj是一个String吗？）
        // 一个转换（将obj转换为String）
        // 一个新的局部变量的声明，以便我们可以使用字符串值
        if (o instanceof String) {
            String s = (String) o;
            System.out.println(s.length());
        }

        // jdk14
        // 类型之后添加了变量 s，如果类型检查通过，会被转换并赋值给变量s
        if (o instanceof String s) {
            System.out.println(s.length());
        }

    }
}
