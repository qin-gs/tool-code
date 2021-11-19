package com.effective.chapter06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@DisplayName("枚举 和 注解")
public class Chapter06Test {

    /**
     * 用 enum 代替 int 常量
     */
    @Test
    public void test34() {
        // 通过公有的静态 final 域为每个枚举常量导出一个实例
        // 没有可访问的构造器，是单例的泛型化。本质是单元素的枚举

        // 将数据 与 枚举常量 关联起来，需要声明实例域，并编写一个带有数据并将数据保存在域中的构造函数

        // 静态values方法按照顺序返回值的数组，toString 返回每个枚举值的声明名称
        System.out.println(Arrays.toString(Orange.values()));
        // 可以为枚举添加抽象方法，定义每个枚举值的时候都必须实现该方法

        double x = 1024.0;
        double y = 8.0;
        for (Operation operation : Operation.values()) {
            System.out.printf("%f %s %f = %f%n", x, operation, y, operation.apply(x, y));
        }

        // 特定于常量的方法实现可以与特定于常量的数据结合起来
        // 枚举类中会自动产生一个 valueOf(String) 方法，将常量的名字转变成常量本身
        System.out.println(Orange.valueOf("NAVEL"));

    }
}

enum Orange {
    NAVEL, TEMPLE, BLOOD
}

enum Operation {
    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    private static final Map<String, Operation> stringToEnum =
            Arrays.stream(values()).collect(Collectors.toMap(Object::toString, x -> x));

    /**
     * 将常量的名字转变成枚举类本身
     */
    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }

    // 在枚举类中声明一个抽象方法
    // 特定于常量的方法实现: 对于每个枚举值，用具体的方法覆盖该抽象方法
    public abstract double apply(double x, double y);
}