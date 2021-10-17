package com.example.javacode.code;

import org.junit.jupiter.api.Test;

public class ExtendsTest2 {

    @Test
    public void test() {
        Child_ c = new Child_();
        c.funChild();
        c.funFather();
    }
}

class Father_ {

    /**
     * private静态绑定，在编译的时候就可以确定是调用哪个方法
     */
    private void print() {
        System.out.println("Father");
    }

    public void funFather() {
        this.print();
    }
}

class Child_ extends Father_ {

    public void print() {
        System.out.println("Child");
    }

    public void funChild() {
        this.print();
    }
}