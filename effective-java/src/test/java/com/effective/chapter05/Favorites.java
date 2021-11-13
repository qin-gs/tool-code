package com.effective.chapter05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型安全的异构容器 (typesafe heterogeneous container)
 * key可以是任意类型
 *
 * 两个局限性：
 * 1. 可以使用原生类型破环类型安全
 * 2. 不能用在不可具象化的类型中
 */
public class Favorites {
    // 这个 Map 不能保证键和值的类型关系，因为是 Object
    private final Map<Class<?>, Object> favorites;

    public Favorites(int size) {
        favorites = new HashMap<>(size);
    }

    /**
     * 一个类的字面被用在方法中，来传达编译和运行时的类型信息时，被称为 类型令牌
     */
    public <T> void putFavorite(Class<T> type, T instance) {
        // favorites.put(type, instance);
        // 这样可以检查对象的类型
        favorites.put(type, type.cast(instance));
    }

    public <T> T getFavorite(Class<T> type) {
        // 动态转换成 Class 对象表示的类型
        return type.cast(favorites.get(type));
    }

    public static void main(String[] args) {
        Favorites f = new Favorites(4);
        f.putFavorite(String.class, "a String");
        f.putFavorite(Integer.class, 12);
        f.putFavorite(Class.class, ArrayList.class);

        String s = f.getFavorite(String.class);
        Integer i = f.getFavorite(Integer.class);
        Class<?> c = f.getFavorite(Class.class);

        System.out.printf("%s, %x, %s, %n", s, i, c.getName());
    }
}
