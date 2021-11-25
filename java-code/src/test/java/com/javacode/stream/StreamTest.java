package com.javacode.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@DisplayName("一些流操作")
public class StreamTest {

    @Test
    public void test() {
        Stream<String> stream = Stream.of("a,b,c,d,e", "w,x,y,z");
        // List<String> collect = stream.map(x -> x.split(","))
        //         .flatMap(Arrays::stream)
        //         .collect(Collectors.toList());
        // System.out.println(collect);

        stream.flatMap(x -> Arrays.stream(x.split(",")))
                .forEach(System.out::println);

        // flatMap 将多个流合成一个
        String[][] data = {{"a", "b"}, {"c", "d"}, {"e", "f"}};
        Arrays.stream(data).flatMap(Arrays::stream)
                .forEach(System.out::println);

    }

    /**
     * 寻找1000以内的素数
     */
    @Test
    public void primeTest() {
        long count = Stream.iterate(0, n -> n + 1)
                .limit(1000)
                .filter(StreamTest::isPrime)
                .peek(x -> System.out.format("%s\t", x))
                .count();
        System.out.println(count);

        Stream.iterate(BigInteger.TWO, BigInteger::nextProbablePrime)
                .limit(168)
                .forEach(x -> System.out.format("%s\t", x));
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        // rangeClosed 包含最后的上限
        // range 不包含最后
        return IntStream.rangeClosed(2, num / 2).noneMatch(i -> num % i == 0);
    }
}
