package com.example.javacode;

public class Test {
    public void test() {
        int i = 8;
        while ((i -= 3) > 0) ;
        System.out.println("i = " + i);
    }

    public static void main(String[] args) {
        Test test = new Test();
        for (int i = 0; i < 5_0000; i++) {
            test.test();
        }
    }
}
