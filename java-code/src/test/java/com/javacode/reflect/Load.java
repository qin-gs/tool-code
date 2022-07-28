package com.javacode.reflect;

public class Load {

    static {
        System.out.println("load static");
    }

    static String staticField = setStaticField();

    public static String setStaticField() {
        System.out.println("给静态代码块赋值");
        return "new staticField";
    }
}
