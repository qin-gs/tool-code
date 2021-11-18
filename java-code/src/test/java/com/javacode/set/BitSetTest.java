package com.javacode.set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

@DisplayName("BitSet 测试")
public class BitSetTest {

    /**
     * 在一个特定的位置上(往往是数组下标的位置上)的值(开关)，
     * 0为没有出现过，1表示出现过，
     * 也就是说使用的时候可根据某一个位置是否为0表示此数(这个位置代表的数，往往是下标)是否出现过。
     */
    @Test
    public void test() {

        // 如果传进去 65，会 new long[wordIndex(nbits-1) + 1];
        // 一个long是64位，所以用两个 long 存储信息
        BitSet s = new BitSet(65);
        BitSet t = new BitSet(65);
        BitSet valueOf = BitSet.valueOf(new long[]{123L});

        // 计算机底层的移位操作，如果移动的位数超过了该类型的最大位数，那么编译器会对移动的位数取模。
        // 对 long 类型来说，移动 65位，实际上只移动了 1 位

        // 先算出修改第几个long的下标 bitIndex >> ADDRESS_BITS_PER_WORD (就是除以64)
        // 然后判断是否需要扩容 (如果需要的长度大于数组的两倍，扩容到需要的长度；否则扩容到数组的两倍)
        // 根据下标找到对应的long，修改它的值  words[wordIndex] |= (1L << bitIndex);
        s.set(65);

        // set 将第3位改成1
        // 1. 将 1 左移 3 位   0000 1000
        // 2. 和原来的值进行或操作得到结果  (0000 1000 | 0000 0000)

        boolean b = s.get(3);
        // get 获取第3位的值
        // 1. 将 1 左移 3 位 0000 1000
        // 2. 和原来的值进行与运算得到结果 (0000 1000 & 0000 0000)

        s.clear(3);
        s.clear();
        // 清除指定位置的标记，这可能导致 wordInUse(表示数组中最多使用的元素个数，也就是最后一个不为 0 的元素的索引加 1)变化
        // 清除全部操作，将所有的long都改成0

        s.and(t);
        // 与操作
        // 两个 BitSet 做与操作，只需要对 共同的部分 进行按位与操作即可

        s.or(t);
        s.xor(t);
        // 或/异或 操作
        // 可能引起扩容
    }

    /**
     * 产生 1000_0000 个 0 - 1_0000_0000 之间的随机数，找出没有出现过的数字
     */
    public void checkDigits() {
        Random random = new Random();

        // 产生一些随机数
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000_0000; i++) {
            int randomResult = random.nextInt(1_0000_0000);
            list.add(randomResult);
        }

        // 在 BitSet 中设置存在的随机数
        BitSet bitSet = new BitSet(1_0000_0000);
        for (int i = 0; i < 1000_0000; i++) {
            bitSet.set(list.get(i));
        }

        // 找到不存在的随机数
        System.out.println("0~1亿不在上述随机数中有" + bitSet.cardinality());
        for (int i = 0; i < 1_0000_0000; i++) {
            if (!bitSet.get(i)) {
                System.out.println(i);
            }
        }
    }
}
