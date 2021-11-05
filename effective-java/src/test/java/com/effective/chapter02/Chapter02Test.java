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
}

