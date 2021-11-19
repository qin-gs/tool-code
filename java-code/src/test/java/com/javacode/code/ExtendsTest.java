package com.javacode.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("测试this super")
public class ExtendsTest {

    /**
     * this总是指向调用该方法的对象
     * 而super总是用于调用处方法所处的类的直接父类
     */
    @Test
    public void test() {
        Child child = new Child();
        child.funChild(); // Child Father
        child.funFather(); // Child GrandFather
    }

}

class GrandFather {
    public void print() {
        System.out.println("GrandFather");
    }
}

class Father extends GrandFather {
    @Override
    public void print() {
        System.out.println("Father");
    }

    public void funFather() {
        this.print();
        super.print();
    }
}

class Child extends Father {
    @Override
    public void print() {
        System.out.println("Child");
    }

    public void funChild() {
        this.print();
        super.print();
    }
}