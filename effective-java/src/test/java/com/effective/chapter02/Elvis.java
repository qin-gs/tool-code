package com.effective.chapter02;

/**
 * 公有的成员是个静态 final 对象
 */
public class Elvis {
    public static final Elvis instance = new Elvis();

    private Elvis() {
    }

    /**
     * 保证反序列化时不会创造新的对象
     */
    private Object readResolve() {
        return instance;
    }
}

/**
 * 公有的成员是个方法
 */
class Elvis_ {
    private static final Elvis_ instance = new Elvis_();

    private Elvis_() {
    }

    public static Elvis_ getInstance() {
        return instance;
    }
}

/**
 * 单元素枚举类型实现单例
 */
enum Elvis__ {
    instance;

}