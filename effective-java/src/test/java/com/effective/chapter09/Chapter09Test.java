package com.effective.chapter09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("通用编程")
public class Chapter09Test {

    /**
     * 57. 将局部变量的作用域最小化
     * 58. for-each 优先于传统的 for 循环
     */
    @Test
    public void test57() {
        // 在第一次使用它的地方声明
        // 几乎每一个局部变量的声明都应该包含一个初始表达式(try-catch例外)
        // for 循环优先于 while(for 循环遍历的作用域更小)
        // 使方法小而集中

        // 解构过滤：如果需要遍历删除集合中的某些元素
        // 转换：如果需要遍历替换集合中的某些元素
        // 平行迭代：并行遍历多个集合(避免控制多个索引变量)

        List<String> list = List.of("a", "b", "c");
        // 用两个变量，n 用来存储第一个变量的极限值
        for (int i = 0, n = list.size(); i < n; i++) {
            System.out.println(list.get(i));
        }
    }
}
