package com.effective.chapter09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@DisplayName("通用编程")
public class Chapter09Test {

    /**
     * 57. 将局部变量的作用域最小化
     * 58. for-each 优先于传统的 for 循环
     */
    @Test
    public void test57() {
        // 在第一次使用它的地方声明
        // 几乎每一个局部变量的声明都应该包含一个初始表达式(try-catch例外)
        // for 循环优先于 while(for 循环遍历的作用域更小)
        // 使方法小而集中

        // 解构过滤：如果需要遍历删除集合中的某些元素
        // 转换：如果需要遍历替换集合中的某些元素
        // 平行迭代：并行遍历多个集合(避免控制多个索引变量)

        List<String> list = List.of("a", "b", "c");
        // 用两个变量，n 用来存储第一个变量的极限值
        for (int i = 0, n = list.size(); i < n; i++) {
            System.out.println(list.get(i));
        }
    }

    /**
     * 59. 了解和使用类库
     */
    @Test
    public void test59() throws Exception {
        // Integer.MIN_VALUE 绝对值和它本身相等   a - b = a + (-b)
        // -2147483648
        // 原码  0x 1000 0000 0000 0000 0000 0000 0000 0000
        // 反码  0x 0111 1111 1111 1111 1111 1111 1111 1111
        // 补码  0x 1000 0000 0000 0000 0000 0000 0000 0000 (反码+1)
        System.out.println(Math.abs(Integer.MIN_VALUE) == Integer.MIN_VALUE);

        // jdk7 之后的升级版 Random
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // 打印一个网站的内容
        InputStream stream = new URL("https://www.baidu.com").openStream();
        stream.transferTo(System.out);
    }

    /**
     * 60. 需要精确的情况下避免使用 float double
     * 61. 基本类型(primitive)优先于装箱类型(boxed)
     */
    @Test
    public void test60() {
        // 精确的计算尽量使用 BigDecimal int long

        // 两个包装类型的比较不能用 ==
        // 基本类型 和 包装类型 两者进行比较时，会进行拆箱(可能导致空指针)

        // 集合中的元素，键，值 必须是包装类型
        // 反射的方法调用必须使用包装类型
    }

    /**
     * 62. 如果其他类型更合适，避免使用字符串
     * 63. 了解字符串连接的性能
     */
    @Test
    public void test62() {
        // 字符串不适合代替其他值类型
        // 字符串不适合代替枚举
        // 字符串不适合代替聚合类型
        // 字符串不适合代替能力表(capabilities，比如：ThreadLocale的键)

        // 为连接n个字符串而重复的使用字符串连接操作符，需要 n^2 的时间
        // 使用 StringBuilder，进行初始化长度
    }

    /**
     * 64. 通过接口引用对象
     */
    @Test
    public void test64() {
        // 如果有合适的接口类型存在，对于 参数，返回值，变量，域 都应该使用接口类型声明
        // 只有通过构造器创某个对象的时候，才真正需要引用对象的类
        Set<String> set = new LinkedHashSet<>(); // 修改实现的时候，程序更加灵活

        // 如果没有合适的接口，直接使用类
        // String, BigDecimal 都是 final 的，可以直接用
        // 基于类的框架 (java.io)
        // 类中提供了接口中不存在的方法，如果依赖这些特别的方法，直接用类引用实例 (PriorityQueue#comparator)
    }

    /**
     * 65. 接口优先于反射
     */
    @Test
    public void test65() {
        // 反射的缺陷：
        // 损失了编辑时类型检查的优势(如果企图调用不存在的方法只会在运行时失败)
        // 指向反射访问所需的代码笨拙冗长
        // 反射带来性能损失

        // 某些程序用到的类，在编译时是不可用的，但是编译时存在适当的接口或超类。可以通过他们引用这个类
        // 可以通过反射创建实例，通过接口或超类以正常的方式访问

    }

    public void setTest(String[] args) {
        // cls 在编译时只知道是一个 Set, 具体是哪个类是不确定的
        // Translate the class name into a Class object
        Class<? extends Set<String>> cl = null;
        try {
            cl = (Class<? extends Set<String>>) // Unchecked cast!
                    Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            fatalError("Class not found.");
        }
        // Get the constructor
        Constructor<? extends Set<String>> cons = null;
        try {
            cons = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fatalError("No parameterless constructor");
        }
        // Instantiate the set 可能是 HashSet, TreeSet
        Set<String> s = null;
        try {
            s = cons.newInstance();
        } catch (IllegalAccessException e) {
            fatalError("Constructor not accessible");
        } catch (InstantiationException e) {
            fatalError("Class not instantiable.");
        } catch (InvocationTargetException e) {
            fatalError("Constructor threw " + e.getCause());
        } catch (ClassCastException e) {
            fatalError("Class doesn't implement Set");
        }
        // Exercise the set
        s.addAll(Arrays.asList(args).subList(1, args.length));
        System.out.println(s);

    }

    private void fatalError(String msg) {
        System.err.println(msg);
        System.exit(1);
    }

    /**
     * 66. 谨慎的使用本地方法
     */
    @Test
    public void test66() {
        // 不需要通过本地方法来提高性能
        // 本地方法是不安全的
        // 本地方法是平台相关的，不能自由移植，难以调试，无法追踪内存使用
        // 需要 胶水代码 进行组合
    }

    /**
     * 67. 谨慎的进行优化
     * 68. 遵守普遍接收的命名惯例
     */
    @Test
    public void test67() {
        // 很多计算机上的过是都被归咎于效率(没有达到必要的效率)，而不是任何其他的原因----甚至包括盲目的做傻事
        // 不要去计较效率的一些小小得失，在97%的情况下，不成熟的优化才是一切问题的根源
        // 优化方法，应该遵守两条规则
        //     不要进行优化
        //     不要进行优化，也就是说，在没有决定清晰的优化方案之前，不要进行优化

        // 不要为了性能而牺牲合理的结构，努力编写好的程序而不是快的程序；如果好的程序不够快，结构会使它得到优化
        // 避免限制性能的涉及决策
        // 考虑api设计决策的性能后果
        // 为了更好的性能对api进行包装，是不好的想法
        // 每次试图进行性能优化之前之后，都要进行性能测量
    }

}
