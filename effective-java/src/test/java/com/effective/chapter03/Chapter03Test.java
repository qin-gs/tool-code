package com.effective.chapter03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("对于所有对象都通用的方法")
public class Chapter03Test {

    /**
     * 10. 覆盖 equals 时遵守通用约定
     */
    @Test
    public void test10() {

        // 类的每个实例本质都是唯一的
        // 类没有必要提供 逻辑相等 的测试功能
        // 父类已经覆盖率 equals 方法，父类的行为对于这个类也是合适的(AbstractSet, AbstractList)
        // 类是 private 或 protected ，它的 equals 方法永远不会被调用

        // 重写 equals 方法
        // 希望知道两个对象是否逻辑是否相等，而不是是否指向同一个对象(可以用来作为 Map 的 key，或放入 Set)
        // 单例对象不需要重写 equals 方法

        // 无法在扩展可实例化的类的同时，即增加新的值组件，又保留equals约定
    }

}
