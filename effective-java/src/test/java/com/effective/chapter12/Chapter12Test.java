package com.effective.chapter12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ObjectInputFilter;

@DisplayName("序列化")
public class Chapter12Test {

    /**
     * 85. 其他方法优先于 java序列化
     */
    @Test
    public void test85() {
        // 没有理由使用 java序列化
        // 不要反序列化不被信任的数据

        ObjectInputFilter filter;
    }

    /**
     * 86. 谨慎的实现 Serializable 接口
     */
    @Test
    public void test86() {
        // 一旦类被发布，大大降低了 '改变这个类的实现' 的灵活性
        // 增加出现 bug 和 安全漏洞的可能性
        // 随着类发行新的版本，相关的测试负担会增加

        // 为了继承而设计的类应该尽量少的实现 Serializable 接口，用户的接口也应该尽量少的继承该接口
        // 内部类不要实现该接口，静态成员类可以实现该接口
    }

    /**
     * 87. 考虑使用自定义的序列化形式
     */
    @Test
    public void test87() {
        // 如果一个对象的物理表示法等同于它的逻辑内容，可能适合使用默认的序列化形式
        // 如果默认的序列化形式是合适的，需要提供一个 readObject 方法保证约束关系和安全性

        // 如果一个对象的物理表示法 和 逻辑数据内容 由实质性的区别时：
        //   1. 它时这个类的导出 API 永远束缚在该类的内部表示法省
        //   2. 消耗过多空间
        //   3. 消耗过多时间
        //   4。引入栈溢出

        // defaultWriteObject 方法被调用的时候，每一个未标记为 transient 的实例域都会被序列化
        // 将一个域标记为 非transient 之前，确保它是对象逻辑状态的一部分

        // 使用默认的序列化形式时，被 transient 修饰的域会被反序列化成默认值(0, false, null...)
        // 如果默认值不对，需要添加 readObject 方法(需要先调用 defaultReadObject 方法)，恢复成可接受的值

        // 如果在读取整个对象状态的任何其他方法上强制任何同步，也必须在对象序列化上强制这种同步

        // 无论那种序列化方式，都要显式添加一个 serialVersionUID，如果未添加，会在运行时计算出一个(高开销)
        // 不要修改 serialVersionUID, 否则会破坏现有的已被序列化实例的兼容性
    }

    /**
     * 88. 保护性的编写 readObject 方法
     */
    @Test
    public void test88() {
        // readObject 方法相当于一个公有的构造器
        // 当对象被反序列化时，如果某个域包含了客户端不应该拥有的对象引用，需要在 readObject方法 进行保护性拷贝

        // 对于对象引用域必须保持为私有的类，要保护性的拷贝这些域中的每个对象(不可变类的可变组件就属于这类)
        // 对于任何约束条件，如果检查失败，排除 InvalidObjectException 异常
        // 如果整个对象图在被反序列化之后必须验证，使用 ObjectInputValidation 接口
        // 无论是直接还是间接方式，都不要调用类中任何可能被覆盖的方法

    }

    /**
     * 89. 对于实例控制，枚举类型优先于 readResolve
     */
    @Test
    public void test89() {
        // 执行顺序  readObject -> readResolve
        // 如果依赖 readResolve 进行实例控制，带有对象引用类型的所有实例都必须声明为 transient
    }

    /**
     * 90. 考虑用序列化代理代替序列化实例
     */
    @Test
    public void test90() {
        // 序列化代理模式 serialization proxy pattern
    }
}
