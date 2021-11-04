package com.javacode.stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;

@DisplayName("java8中的流操作")
public class SpliteratorTest {
    static List<Integer> list;
    static Spliterator<Integer> spliterator;

    @BeforeAll
    public static void beforeAll() {
        list = List.of(2, 3, 4, 1);
        spliterator = list.stream().spliterator();
    }

    @Test
    public void tryAdvanceTest() {
        AtomicInteger round = new AtomicInteger(1);
        AtomicInteger loop = new AtomicInteger(1);
        // 如果存在剩余元素，则对其执行给定的操作，返回true ； 否则返回false 。
        // 如果此 Spliterator 是 ORDERED ，则按遇到顺序对下一个元素执行操作。 动作抛出的异常被转发给调用者。
        // 如果操作引发异常，则未指定拆分器的后续行为。
        while (spliterator.tryAdvance(x -> System.out.printf("第%d次调用，值: %d\n", round.getAndIncrement(), x))) {
            System.out.printf("第%d次循环\n", loop.getAndIncrement());
        }
    }

    @Test
    public void forEachRemainingTest() {
        AtomicInteger round = new AtomicInteger(1);
        // 在当前线程中按顺序为每个剩余元素执行给定的操作，直到处理完所有元素或操作引发异常。
        // 如果此 Spliterator 是ORDERED ，则操作按顺序执行。 动作抛出的异常被转发给调用者。
        // 如果操作引发异常，则未指定拆分器的后续行为。
        spliterator.forEachRemaining(x -> System.out.printf("第%d次调用，值: %d\n", round.getAndIncrement(), x));
    }

    @Test
    public void trySplitTest() {
        Spliterator<Integer> first = spliterator;
        // 当前的Spliterator实例X是可分割的，trySplit()方法会分割X产生一个全新的Spliterator实例Y，
        // 原来的X所包含的元素（范围）也会收缩，
        // 类似于X = [a,b,c,d] => X = [a,b], Y = [c,d]；如
        // 果当前的Spliterator实例X是不可分割的，此方法会返回NULL），
        // 具体的分割算法由实现类决定
        Spliterator<Integer> second = spliterator.trySplit();
        first.forEachRemaining(x -> System.out.println("first : " + x));
        second.forEachRemaining(x -> System.out.println("second: " + x));
    }

    @Test
    public void estimateSizeTest() {
        // 遍历的元素总量的估计值，如果样本个数是无限、计算成本过高或者未知，会直接返回Long.MAX_VALUE
        System.out.println(spliterator.estimateSize());
        // 如果当前的Spliterator具备SIZED特性，那么直接调用estimateSize()方法，否则返回-1
        System.out.println(spliterator.getExactSizeIfKnown());
    }

    @Test
    public void getExactSizeIfKnown() {
        Spliterator<Integer> spliterator = list.stream().spliterator();
        // 如果当前Spliterator有 SIZED 特性，直接调用 estimateSize 方法，否则直接返回-1
        long l = spliterator.getExactSizeIfKnown();
        System.out.println(l);

        // 当前 Spliterator 具有的特性集合，采用位运算，保存在32位整数中
        int i = spliterator.characteristics();
        System.out.println(i);

        // 判断当前 Spliterator 是否具备传入的特性
        boolean b = spliterator.hasCharacteristics(Spliterator.DISTINCT);
        System.out.println(b);

        // 如果当前 Spliterator 具有 SORTED 特性，返回比较器
        // 如果是天然有效的，返回null
        // 其他情况抛出异常 IllegalStateException
        Comparator<? super Integer> comparator = spliterator.getComparator();
        System.out.println(comparator);
    }
}
