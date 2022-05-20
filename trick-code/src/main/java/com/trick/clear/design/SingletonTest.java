package com.trick.clear.design;

public class SingletonTest {

    public static void main(String[] args) {

    }

    /**
     * 静态内部类实现单例
     */
    static class Singleton {

        private Singleton() {
        }

        private static final class InstanceHolder {
            private static final Singleton INSTANCE = new Singleton();
        }

        public static Singleton getInstance() {
            return InstanceHolder.INSTANCE;
        }
    }

    /**
     * 懒汉式，双重校验锁
     */
    static class Singleton2 {
        private static Singleton2 INSTANCE;

        private Singleton2() {
        }

        public static Singleton2 getInstance() {
            if (INSTANCE == null) {
                synchronized (Singleton2.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new Singleton2();
                    }
                }
            }
            return INSTANCE;
        }
    }

    /**
     * 枚举实现单例
     */
    static {
        enum Singleton {
            INSTANCE;

            public void doSomething() {
                System.out.println("do something");
            }
        }

        // 调用单例对象中的方法
        Singleton.INSTANCE.doSomething();
    }
}
