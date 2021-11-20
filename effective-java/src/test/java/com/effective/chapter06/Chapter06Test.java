package com.effective.chapter06;

import com.effective.chapter06.enum_.BasicOperation;
import com.effective.chapter06.enum_.ExtendedOperation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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

        System.out.println(Operation.fromString("+"));

        // 返回枚举常量在类型中的数字位置
        // 不要用!!!
        int ordinal = Operation.MINUS.ordinal();
    }

    /**
     * 35. 用实例域代替序数
     * 36. 用 EnumSet 代替 位域
     */
    @Test
    public void test35() {
        // 不要根据枚举的序数导出与它相关联的值，而要将它保存在一个实例域中，通过构造函数传进去

        // 如果枚举类型需要用在集合中，使用 EnumSet

        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
        for (int i = 0; i < plantsByLifeCycle.length; i++)
            plantsByLifeCycle[i] = new HashSet<>();
        List<Plant> garden = List.of(new Plant("a", Plant.LifeCycle.ANNUAL));
        for (Plant p : garden)
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
        }

        // 这里需要手动指定为 EnumMap, 否则默认使用 HashMap
        EnumMap<Plant.LifeCycle, Set<Plant>> enumMap = Arrays.stream(Plant.LifeCycle.values())
                .collect(
                        toMap(
                                value -> value,
                                value -> new HashSet<>(4),
                                (a, b) -> b,
                                () -> new EnumMap<>(Plant.LifeCycle.class)
                        )
                );
        Map<Plant.LifeCycle, Set<Plant>> collect = garden.stream()
                .collect(
                        groupingBy(
                                x -> x.lifeCycle,
                                () -> new EnumMap<>(Plant.LifeCycle.class),
                                toSet()
                        )
                );
    }

    static class Plant {
        enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}

        final String name;
        final LifeCycle lifeCycle;

        Plant(String name, LifeCycle lifeCycle) {
            this.name = name;
            this.lifeCycle = lifeCycle;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 37. 用 EnumMap 代替 序数索引
     */
    @Test
    public void test37() {
        Phase.Transition trans = Phase.Transition.from(Phase.GAS, Phase.SOLID);
        System.out.println(trans);
    }

    /**
     * 38. 用接口模拟可扩展的枚举
     */
    @Test
    public void test38() {
        // 无法编写可扩展的枚举类型，可以通过编写以及实现该接口的基础枚举类型进行模拟
        double a = 1024.0;
        double b = 4;
        printAns(ExtendedOperation.class, a, b);
        printAns(BasicOperation.class, a, b);
    }

    /**
     * 传递不同的操作类
     *
     * @param clazz 类型令牌
     * @param <T>   表示该类型既是 枚举，又是 Operation的子类
     */
    private <T extends Enum<T> & com.effective.chapter06.enum_.Operation>
    void printAns(Class<T> clazz, double x, double y) {
        for (com.effective.chapter06.enum_.Operation op : clazz.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    /**
     * 39. 注解优先于命名模式
     */
    @Test
    public void test39() {
        // 命名模式:
    }

    /**
     * 40. 坚持使用 @Override
     * 41. 用标记接口定义类型
     */
    @Test
    public void test40() {
        // equals 方法的函数声明(参数是Object)
        // public boolean equals(Object obj)

        // 标记接口(不包含方法声明的接口，只表明一个类实现类具有某种属性的接口)
        // Serializable 说明这个类实例可以被写入 ObjectOutputStream 流中

        // 标记接口定义的类型是由被标记类的实例实现的；标记注解没有定义这样的类型
        // 标记接口可以被更加精确的进行锁定
    }
}

enum Orange {
    NAVEL, TEMPLE, BLOOD
}

enum Operation {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
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
            Arrays.stream(values()).collect(toMap(Object::toString, x -> x));

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

enum PayrollDay {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
    SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);

    private final PayType payType;

    /**
     * 无参构造函数
     */
    PayrollDay() {
        this.payType = PayType.WEEKDAY;
    }

    /**
     * 特殊的工作日，使用有参的构造函数
     */
    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    int pay(int minutes, int payRate) {
        return payType.pay(minutes, payRate);
    }

    /**
     * 枚举策略
     * 不同工作日计算工资的方式
     */
    private enum PayType {
        WEEKDAY {
            @Override
            int overtimePay(int minutes, int payRate) {
                return minutes <= MINS_PER_SHIFT ? 0 : (minutes - MINS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            @Override
            int overtimePay(int minutes, int payRate) {
                return minutes * payRate / 2;
            }
        };

        abstract int overtimePay(int minutes, int payRate);

        private static final int MINS_PER_SHIFT = 8 * 60;

        int pay(int minutes, int payRate) {
            int base = minutes * payRate;
            return base + overtimePay(minutes, payRate);
        }

    }
}

enum Phase_ {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;
        // Rows indexed by from-ordinal, cols by to-ordinal
        private static final Transition[][] TRANSITIONS = {
                {null, MELT, SUBLIME},
                {FREEZE, null, BOIL},
                {DEPOSIT, CONDENSE, null}
        };

        // Returns the phase transition from one phase to another
        public static Transition from(Phase_ from, Phase_ to) {
            return TRANSITIONS[from.ordinal()][to.ordinal()];
        }
    }
}

enum Phase {
    // 三种状态
    SOLID, LIQUID, GAS;

    public enum Transition {
        // 三种状态之间的转换
        MELT(SOLID, LIQUID),
        SUBLIME(SOLID, GAS),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        DEPOSIT(GAS, SOLID);

        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        // Initialize the phase transition map
        private static final Map<Phase, Map<Phase, Transition>> m = Stream.of(values())
                .collect(
                        // 按照起始状态进行分组
                        groupingBy(
                                t -> t.from,
                                () -> new EnumMap<>(Phase.class),
                                toMap(
                                        t -> t.to,
                                        t -> t,
                                        (x, y) -> y,
                                        () -> new EnumMap<>(Phase.class)
                                )
                        )
                );

        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
}