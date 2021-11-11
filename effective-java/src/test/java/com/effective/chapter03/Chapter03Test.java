package com.effective.chapter03;

import com.effective.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@DisplayName("对于所有对象都通用的方法")
public class Chapter03Test {

    /**
     * 10. 覆盖 equals 时遵守通用约定
     * <p>
     * 重新 equals 方法：
     * <pre>
     *     1. 使用 == 操作符检查 “参数是否为这个对象的引用”
     *     2. 使用 instanceof 操作非检查 “参数是否为正确的类型”
     *     3. 把参数转换成正确的类型
     *     4. 对于该类中的每个 “关键” 域，简餐参数总中的域是否与该对象中对应的域匹配
     * </pre>
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

        // 自反性 x.equals(x) == true
        // 对称性 x.equals(y) == y.equals(x)
        // 传递性 x.equals(y) == y.equals(z) == z.equals(x)
        // 一致性 多次equals返回结果要相同

        // 无法在扩展可实例化的类的同时，即增加新的值组件，又保留equals约定
        // 不要使 equals 方法依赖不可靠的资源
    }

    /**
     * 11. 覆盖 equals 方法是也要覆盖 hashCode
     * 12. 始终要覆盖 toString
     */
    @Test
    public void test11() {
        // 如果两个对象的 equals 比较是相等的，那么两个对象的 hashCode 结果必须相等
        // 如果类是不可变的并且计算开销比较大，可以提前计算出来写死

        // 这个方法会产生一个数组，效率低，如果参数中有基本类型会拆箱和装箱
        Objects.hash("a", "b");
    }

    /**
     * 13. 谨慎的继承 clone 方法
     */
    @Test
    public void test13() throws Exception {
        // Cloneable 接口
        // Object#clone 方法是 protected
        // 不实现该接口调用 clone 方法会抛出 CloneNotSupportedException
        // 该接口改变了超类中方法的行为
        // clone 方法就是另一个构造器；必须确保它不会伤害到原始对象，并确保正确的创建被克隆对象中的约束条件
        // TODO
    }

    /**
     * 14. 考虑实现 Comparable 接口
     */
    @Test
    public void test14() {
        BigDecimal num1 = new BigDecimal("1.0");
        BigDecimal num2 = new BigDecimal("1");
        System.out.println("num1.equals(num2) = " + num1.equals(num2));
        System.out.println("num1.compareTo(num2) == 0 = " + (num1.compareTo(num2) == 0));

        // BigDecimal 方法
        Set<BigDecimal> hashSet = new HashSet<>();
        hashSet.add(num1);
        hashSet.add(num2);
        System.out.println("hashSet.size() = " + hashSet.size());
        System.out.println("hashSet = " + hashSet);

        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(num1);
        treeSet.add(num2);
        System.out.println("treeSet.size() = " + treeSet.size());
        System.out.println("treeSet = " + treeSet);

    }

}
