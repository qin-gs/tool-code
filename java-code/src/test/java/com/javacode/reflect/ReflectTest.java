package com.javacode.reflect;

/**
 * Class.forName() 会调用静态代码块
 */
public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("1. ----------------");
        // 会调用 static 中的静态代码块，给静态变量赋值的方法也会被调用
        Class.forName("com.javacode.reflect.Load");

        System.out.println("2. ----------------");
        // 第二个参数为false，不会调用静态代码块
        Class.forName("com.javacode.reflect.Load", false, ReflectTest.class.getClassLoader());

        System.out.println("3. ----------------");
        // 通过 ClassLoader 加载 class 不会调用静态代码块
        ReflectTest.class.getClassLoader().loadClass("com.javacode.reflect.Load");
    }
}
