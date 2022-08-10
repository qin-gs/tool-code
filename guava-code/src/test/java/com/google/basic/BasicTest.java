package com.google.basic;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Comparators;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.pojo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.*;


@DisplayName("基本工具")
public class BasicTest {

    @Test
    public void optional() {
        Object o = new Object();

        // 创建指定引用的 Optional 实例，若引用为 null 则快速失败
        Optional<Object> o1 = Optional.of(o);
        // 	创建引用缺失的Optional实例
        Optional<Object> absent = Optional.absent();
        // 创建指定引用的 Optional 实例，若引用为 null 表示缺失
        Optional<Object> objectOptional = Optional.fromNullable(o);


        // 判断引用是否存在
        boolean present = o1.isPresent();
        // 获取引用，若引用为 null 则抛出异常
        Object get = o1.get();
        // 获取引用，若引用为 null 则返回指定的值
        Object or = o1.or(new Object());
        // 获取引用，若引用为 null 则返回 null
        Object orNull = o1.orNull();
        // 返回 optional 包含引用的单例不可变集合，如果引用存在，返回一个只有一个元素的集合，若引用为 null 则返回空集合
        Set<Object> set = o1.asSet();
    }

    /**
     * 前置条件判断
     */
    @Test
    public void preCondition() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int i = random.nextInt(100);
        int j = random.nextInt(100);

        // 没有额外参数，抛出的异常没有错误信息
        checkArgument(i > j);
        // 有额外参数，抛出的异常使用 Object.toString 作为错误信息
        checkArgument(i > j, "参数错误");
        // 有错误信息，可以包含变长参数将错误信息进行格式化
        checkArgument(i > j, "希望 i > j，但是 %s > %s", i, j);


        // 检查 boolean
        checkArgument(i > j);
        // 检查是否为 null
        checkNotNull("a");
        // 检查状态
        checkState(i > j);
        // 检查某个索引对列表是否有效 (index >= 0 && index < size)
        checkElementIndex(3, 5);
        checkPositionIndex(3, 5);
        checkPositionIndexes(2, 4, 6);
    }

    @Test
    public void obj() {
        // 排序器
        // 首先调用 Function#apply 方法获取值，并把为 null 的元素都放到最前面，然后把剩下的元素按自然顺序进行排序
        Ordering<User> ordering = Ordering.natural()
                .nullsFirst()
                // 对集合中的元素调用 Function，按返回值排序
                .onResultOf(User::getName);


        class User implements Comparable<User> {
            private String name;
            private int age;


            @Override
            public int compareTo(User o) {
                return ComparisonChain.start()
                        .compare(this.name, o.name)
                        .compare(this.age, o.age)
                        .result();
            }
        };
    }
}
