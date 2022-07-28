package com.javacode.map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Map 相关操作")
public class MapTest {

    @Test
    public void test() {
        // 如果v已经计算好了，那么适合使用putIfAbsent(k, v)，
        // 如果v还未计算，同时计算需要一些耗时,那么建议使用computeIfAbsent，
        // 将获取v值的计算放到lambada表达式体内，这样只有再map不含有k对应值时才会进行获取v值的计算，
        // 可以优化性能

        Map<String, String> map = new HashMap<>(4);
        // 如果存在，返回key对应的值
        // 如果不存在插入并返回
        map.putIfAbsent("a", "b");
        // 如果存在，返回key对应的值
        // 如果不存在将第二个参数的返回值存入并返回
        map.computeIfAbsent("a", x -> "value");

        // 计数
        Map<Character, Integer> count = new HashMap<>();
        count.put('a', count.getOrDefault('a', 0) + 1);
    }
}
