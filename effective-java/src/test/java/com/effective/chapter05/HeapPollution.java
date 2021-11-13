package com.effective.chapter05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@DisplayName("堆污染测试")
public class HeapPollution {

    @Test
    public void danger() {
        // 这里 pickTwo 的返回类型是 Object[]，会进行一个隐式的转换
        // 抛出 ClassCastException 异常  [Ljava.lang.String -> [Ljava.lang.Object
        String[] o = pickTwo("a", "b", "c");
        System.out.println(Arrays.toString(o));
    }

    /**
     * 泛型可变参数数组
     */
    public <T> T[] toArray(T... args) {
        return args;
    }

    /**
     * 让一个方法访问 泛型可变参数数组 是不安全的
     */
    public <T> T[] pickTwo(T a, T b, T c) {
        // 创建一个可变参数数组，将两个实例传到 toArray
        // 代码配置了一个 Object[] 的数组，这是确保能保存这些实例的最具体类型
        // 改方法的返回值是 Object[]
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return toArray(a, b);
            case 1:
                return toArray(a, c);
            case 2:
                return toArray(b, c);
        }
        throw new AssertionError();
    }

}
