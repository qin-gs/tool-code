package com.effective.chapter07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

@DisplayName("lambda 和 stream")
public class Chapter07Test {

    /**
     * 42. lambda 优先于匿名类
     */
    @Test
    public void test42() {
        // java8 之前
        // 用带有单个抽象方法的接口作为函数类型，他们的实例被称为 函数对象(function object)
        // 创建函数对象的方式 -> 匿名类
        // 策略(strategy)

        // java8 之后
        // 函数式接口(只有一个抽象方法的接口)
        // 编译器通过类型推导(泛型)的方式确定 参数类型 和 返回值类型

        // lambda 无法获得对自身的引用
        // lambda 中this指的是外围实例
        // 匿名内部类中的this指的是匿名类实例，如果需要在函数中访问它，只能使用匿名内部类

        // 不要给函数对象使用匿名类，除非必须创建非函数接口类型的实例
    }

    /**
     * 43. 方法引用优先于lambda
     */
    @Test
    public void test43() {
        Map<String, Integer> map = new HashMap<>(4);
        // 如果指定的key没有映射，就插入指定值
        // 如果存在，会将指定的函数应用到当前值和指定值上，并用结果覆盖当前值
        map.merge("a", 1, (count, incr) -> count + incr);
        map.merge("a", 1, Integer::sum);

        // 返回自身的函数
        Function<Object, Object> identity = Function.identity();
        Function<Object, Object> self = t -> t;

        // 静态方法
        Function<String, Integer> biFunction = Integer::parseInt;
        Function<String, Integer> strToInteger = (String str) -> Integer.parseInt(str);
        // 有限制
        Function<Instant, Boolean> isAfter = Instant.now()::isAfter;
        Function<Instant, Boolean> instantFilter = (Instant t) -> Instant.now().isAfter(t);
        // 无限制
        Function<String, String> function = String::toLowerCase;
        Function<String, String> stringFunction = (String str) -> str.toUpperCase(Locale.ROOT);
        // 类构造器
        Supplier<TreeMap<String, Integer>> supplier = TreeMap<String, Integer>::new;
        Supplier<TreeMap<String, Integer>> sup = () -> new TreeMap<>();
        // 数组构造器
        IntFunction<int[]> aNew = int[]::new;
        IntFunction<int[]> intFunction = (int len) -> new int[len];

        int[] apply = aNew.apply(2);
        System.out.println(Arrays.toString(apply));

        BiFunction<Integer, Integer, int[][]> bi = (x, y) -> new int[x][y];

    }

    /**
     * 44. 坚持使用标准的函数接口
     */
    @Test
    public void test44() {
        // java.util.function 包中的六类接口(43个)
        // Operator 结果与参数类型一致
        //   UnaryOperator 一个参数，一个返回值
        //   BinaryOperator 两个参数，一个返回值
        // Predicate 一个参数，返回boolean类型
        // Function 参数类型与返回值不一致
        // Supplier 没有参数，有一个返回值
        // Consumer 一个参数，没有返回值

        // 都有三个变体 int, long, double
        // Function 9种变体(参数和返回值类型不同)
        // self -> self  UnaryOperator
        // src -> dest   SrcToDest

        // BooleanSupplier 返回boolean类型

        // 不要用带包装类型的基础函数接口代替基本函数接口
        // 使用 @FunctionalInterface 注解对函数式接口进行标注(避免添加新的抽象方法)
        // 不要再相同的参数位置，提供不同的函数接口进行多次重载的方法

    }

    /**
     * 45. 谨慎使用Stream
     */
    @Test
    public void test45() {
        // Stream(流): 代表数据元素有限或无限的顺序
        // Stream pipeline(流管道): 代码元素的一个多级运算

        // 源 -> 中间操作 -> 终止操作
        // 流管道式lazy，直到调用终止操作才会开始计算，对于完成终止操作不需要的数据元素，永远不会被计算

        // 没有显式类型的情况下，仔细命名lambda的参数，提高可读性
        // 给操作命名，不在主程序中保留实现细节

        // java 不支持基本类型的stream
        // chars() 方法返回 IntStream，避免用流处理char
        "hello world!".chars().forEach(System.out::println);
        "hello world!".chars().forEach(x -> System.out.println(((char) x)));

        // 流无法处理的一些场景：
        // 在代码块中，可以读取或修改范围内的任意局部变量；lambda只能读取final或有效的final变量，且不能修改任何local变量
        // 在代码块中，可以从外围方法return, break, continue，或抛出受检异常；lambda无法完成

        // 统一转换元素序列
        // 过滤元素序列
        // 利用单个操作(添加，连接，计算最小值)合并元素顺序
        // 将元素的序列放入集合，根据属性进行分组
        // 过滤满足某些条件的元素

    }

    /**
     * 46. 优先选择 Stream 中无副作用的函数
     */
    @Test
    public void test46() {

    }
}

