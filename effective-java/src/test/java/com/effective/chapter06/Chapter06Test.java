package com.effective.chapter06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@DisplayName("泛型 generic")
public class Chapter06Test {

    /**
     * 26. 不要使用原生类型
     */
    @Test
    public void test26() {

        // List<String>
        // 参数化类型: String
        // 原始类型: List

        // ? 通配符声明的集合不允许添加元素
        List<?> c = new ArrayList<>();

        // 必须使用原始类型的场景
        // 1. 获取 class 对象
        Class<List> clazz = List.class;
        // 2. instanceof
        if (c instanceof Set) {
            Set<?> s = (Set<?>) c;
        }
    }

    /**
     * 27. 消除未检查(unchecked)异常
     */
    public void test27() {
        ArrayList<String> list = new ArrayList<>();
        // 这个方法内部有一个 @SuppressWarnings("unchecked")
        // 该注解应在尽量小的作用域上使用，不要在类上声明!!!
        list.toArray(new String[0]);
    }

    /**
     * 28. 列表优先于数组
     * 29. 优先考虑泛型
     * 30. 优先使用泛型方法
     */
    @Test
    public void test28() {
        // Long[] 是 Object[] 的子类型
        Object[] longs = new Long[1];
        longs[0] = "a string in long[]"; // ArrayStoreException

        // List<Long> 不是 List<Object> 的子类型 (covariant 协变)
        // List<Object> list = new ArrayList<Long>();
        // List<String>[] 是不合法的
        // E, List<T>, List<String> 不可具化类型(non-reifiable type)
        // 由于泛型擦除的原因，运行时展示的信息比编译时的信息要少
        // List<?>, Map<?, ?> 可具化参数类型(无限制通配符)

        // 类型限制<E extends Comparable<E>>可以被读作“任意能与其自身进行比较的类型E”
    }

    /**
     * 31. 使用有限制通配符增加 API 灵活性
     */
    @Test
    public void test31() {
        // src 作为生产者，只能向外取
        // void pushAll(Iterable<? extends Number> src)
        // dest 作为消费者，只能往里放
        // void popAll(Collection<? super Integer> dest)

        // Comparable<? super T>
        // public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll)

        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        // 生产者
        Integer[] integers = {5, 6, 7};
        stack.pushAll(Arrays.stream(integers).toList());
        System.out.println(stack);

        // 消费者
        ArrayList<Number> dest = new ArrayList<>();
        stack.popAll(dest);
        System.out.println(dest);

    }

}
