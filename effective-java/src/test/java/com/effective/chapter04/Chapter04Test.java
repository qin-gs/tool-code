package com.effective.chapter04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("类和接口")
public class Chapter04Test {

    /**
     * 15. 使类和成员的可访问性最小化
     */
    @Test
    public void test15() {
        // 尽可能地使每个类或成员不被外界访问

        // 降低不必要的公有类的可访问性，比降低包私有的顶层类的可访问性重要
        // private 类级别私有
        // default 包级别私有
        // protected 该类的子类
        // public 无限制

        // 公有类的实例域决不能是公有的，包含公有可变域的类通常不是线程安全的
        // 让类具有公有的静态final数组域，或者返回这种域的访问方法，是错误的

        // 这是错误的，数组中的值可以被改变
        // public static final String[] VALUE = {"a", "b"};
        // 可以使用 Collections.unmodifiableList()
        // 或 增加一个get方法，返回数组的拷贝 VALUE.clone()

        // java9 模块系统
        // 模块通过 模块声明 中的导出声明显式的导出一部分包(这部分声明到 module-info.java 文件中)
        // 未导出的部分在模块之外使不可访问的；模块内部，可访问性不受导出声明的影响
        // 使用模块系统在模块内部的包之间共享类，不对模块外可见
    }

    /**
     * 16. 要在公有类而非公有域中使用访问方法
     */
    @Test
    public void test16() {
        // 如果类可以在它所在的包之外被访问，就提供访问方法
        // 如果类是包级私有的，或是私有的嵌套类，可以直接暴露它的数据域
    }

    /**
     * 17. 是可变性最小化
     */
    @Test
    public void test17() {
        // String, BigDecimal, BigInteger
        // 不可变类：每个实例中包含的信息必须在创建的时候提供，在对象的整个生命周期中不可变
        // 1. 不提供任何修改对象状态的方法 (set)
        // 2. 保证类不会被扩展 (final 或 私有化构造函数，提供静态工厂方法)
        // 3. 声明的所有域都是 final 的
        // 4. 声明的所有域都是私有的
        // 5. 确保对于任何可变组件的互斥访问(如果类具有指向可变对象的域，必须确保用户无法获得指向这些对象的引用；也不要用用户提供的对象初始化这些域)

        // 函数式方法：对操作数进行运算但不修改它(介词 plus, minus, times, divide)
        // 过程式方法：将过程作用在操作数上，使其改变(动词 add)

        // 不可变对象是线程安全的，不需要同步；可以被共享(尽量重用现有的实例(缓存池))
        // 不可变对象之间可以共享他们的内部信息(BigInteger#negate方法内部共享一个数组)
        // 不可变对象为其他对象提供的大量构建
        // 不可变对象提供了失败的原子性(不存在临时不一致的可能性)
        // 缺点：对每个不同的值都需要一个单独的对象

        // 构造函数应该创建完全初始化的对象，并建立起所有的约束关系

    }

    /**
     * 18. 组合(composition)优先于继承(inheritance)
     */
    @Test
    public void test18() {
        // 继承打破封装性
        // 可以用组合，创建一个转发类(负责调用原生方法(转发方法))，里面包装一个原始对象，通过构造函数传进去
        // 创建一个新类，修改这个类并不会影响原始的类

        // Properties 类不应该继承 HashTable
        Properties p = new Properties();
        p.setProperty("key", "value");

        p.getProperty("key"); // 推荐这个方法，会返回字符串
        p.get("key"); // 这个方法不应该调用
    }

    /**
     * 19. 要么设计继承提供文档说明，要么禁止继承
     */
    @Test
    public void test19() {
        // 类必须有文档说明它的可覆盖方法的自用型 (self-use)
        // 比如 HashMap 的 addAll 方法调用了 add

        // {@inheritDoc} 继承文档
        // @implSpec 实现要求 -tag "apiNote:a:API Note"

        // 类必须以精心挑选的受保护的方法的形式，提供适当的额钩子以便进入其内部工作

        // 移除指定范围内的元素
        ArrayList<String> list = new ArrayList<>(List.of("a", "b", "c", "d", "e"));
        list.subList(1, 3).clear();
        System.out.println(list);

        // 对于为了继承而设计的类，唯一的方法就是编写子类；发布类之前必须进行测试
        // 构造函数不能调用可被覆盖的方法(超类的构造器先被执行)

        // Cloneable, Serializable 接口中的 clone, readObject 方法都不能调用可覆盖的方法(无论直接或间接)
        // Cloneable#clone 覆盖的方法有机会修正被克隆对象的状态之前被运行
        // Serializable#readObject 覆盖的方法将在子类的状态被反序列化之前先被调用

        // 对于并非为了安全的进行子类化而设计和编写文档的类，禁止子类化 (final 或 构造函数私有化)
    }

    /**
     * 20. 接口优于抽象类
     */
    @Test
    public void test20() {
        // 现有的类可以很容易被更新，以实现新的接口
        // 接口时定义混合类型(mixin，某些可供选择的行为)的理想选择
        // 接口允许构造非层次结果的类型框架
        // 接口可以安全的增强类的功能

        // 骨架实现(skeletal implementation)
        // 接口负责定义类型，或提供一些缺省方法
        // 骨架类负责实现除基本类型接口方法之外，剩下的非基本类型接口方法
    }

    public List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);

        return new AbstractList<>() {
            @Override
            public Integer get(int index) {
                return a[index];
            }

            @Override
            public Integer set(int index, Integer element) {
                int old = a[index];
                a[index] = element;
                return old;
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }

    /**
     * 21. 为后代设计接口
     */
    @Test
    public void test21() {

    }
}
