package com.effective.chapter02;

import com.effective.chapter02.bean.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

@DisplayName("创建和销毁对象")
public class Chapter02Test {

    /**
     * 1. 用静态工厂方法代替构造函数
     */
    @Test
    public void test1() {
        // 静态工厂方法与构造器的不同点：
        // 优点
        // 1. 它具有名称
        // 2.不用要每次都创建一个新对象
        // 3. 可以返回原返回类型的任何子类型对象
        // 4. 返回对象的类可以随着工厂方法的参数中而发生变化
        // 5. 方法返回对象所属的类，在编写包含该静态方法的类是可以不存在
        // 缺点
        // 1. 类如果不含共有的构造器，不能被子类实例化
        // 2. 难以发现
        BigInteger bigInteger = BigInteger.probablePrime(10, new Random());
        System.out.println(bigInteger);
    }

    /**
     * 2. 遇到多个构造器参数时考虑使用构造器
     */
    @Test
    public void test2() {
        // 使用final并且不提供setter使构造出来的对象不可变
        NutritionFacts facts = new NutritionFacts.Builder(10, 2)
                .calories(100).sodium(35).carbohydrate(27).build();
        System.out.println(facts);

        // 构造出来的对象是可变的
        User user = new User.Builder().withUsername("a username").withPassword("a password").build();
        System.out.println(user);

        // 类层次的建造者 Builder
        // 抽象类 和 具体类 都有自己的builder
        NyPizza pizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.HAM).addTopping(Pizza.Topping.ONION).build();
        System.out.println(pizza);
        Calzone calzone = new Calzone.Builder()
                .addTopping(Pizza.Topping.MUSHROOM).addTopping(Pizza.Topping.PEPPER).build();
        System.out.println(calzone);
    }

    /**
     * 3. 使用私有构造器 或 枚举类 强化单例
     */
    @Test
    public void test3() {
        // 公有静态对象引用 或 公有静态方法 提供单例对象
        // 单元素枚举类实现单例
        // 需要提供 readResolve 方法使对象反序列化时不会创建新实例
    }

    /**
     * 4. 通过私有构造器强化不可实例化的能力
     */
    @Test
    public void test4() {
        // 工具类添加一个私有构造器使其不可实例化
        // Suppresses default constructor, ensuring non-instantiability.
    }

    /**
     * 5. 优先考虑依赖注入来引入资源
     */
    @Test
    public void test5() {
        // 不要用单例和静态工具类来实现依赖一个或多个底层资源的类，且该资源的行为会应应先到该类的行为
        // 也不要用这个类来创建资源
        // 应该经这些资源或工厂传给构造器，通过他们来创建类
    }

    /**
     * 6. 避免创建不必要的对象
     * 7. 消除过期的对象引用
     * 类自己管理的内存(return stack[-- size] 数组中size后面的元素不会被使用也不会被回收)
     * 缓存(如果缓存项的声明周期是由键的外部引用而不是由值决定时，可以使用 WeakHashMap )
     * 监听器和回调
     */
    @Test
    public void test6() {
        // 使用 静态工厂方法优先于构造器
        // Boolean aBoolean = Boolean.valueOf("true");
        // 正则表达式的编译复用
        // 优先使用基本类型而不是包装类型，当心无意义的自动装箱

        long l = System.currentTimeMillis();
        Long sum = 0L; // 这里使用 long 会快 10 倍
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        long c = System.currentTimeMillis();
        System.out.println(c - l);
        System.out.println(sum);
    }

    /**
     * 8. 避免使用终结方法 和 清除方法
     * 9. try-with-resource 优先于 try-finally
     */
    @Test
    public void test8() {
        // 不要使用总结方法 或 清除方法 更新重要的持久状态
        // finalizer cleaner

    }



}