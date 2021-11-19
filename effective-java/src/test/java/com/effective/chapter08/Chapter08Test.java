package com.effective.chapter08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;

@DisplayName(("lambda 和 stream"))
public class Chapter08Test {

    /**
     * 42. lambda 优先于匿名类
     */
    @Test
    public void test42() {
        // java8 之前
        // 用带有单个抽象方法的接口作为函数类型，他们的实例被称为 函数对象(function object)
        // 创建函数对象的方式 -> 匿名类
        // 策略(strategy)

        // java8 之后
        // 函数式接口(只有一个抽象方法的接口)
        // 编译器通过类型推导(泛型)的方式确定 参数类型 和 返回值类型
        //
    }
}
