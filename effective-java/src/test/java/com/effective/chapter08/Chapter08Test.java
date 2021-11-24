package com.effective.chapter08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("方法")
public class Chapter08Test {

    /**
     * 49. 检查参数的有效性
     */
    @Test
    public void test49() {
        String arg = "";
        // 进行参数检查
        Objects.requireNonNull(arg, "参数不能为空");
        Objects.checkFromIndexSize(2, 3, 1);
        Objects.checkFromToIndex(2, 4, 2);
        Objects.checkIndex(2, 4);
    }

    /**
     * 50. 必要时进行保护性拷贝
     */
    @Test
    public void test50() {
        // Date 不应在新代码中使用，Instant 是不可变的，每次修改都会返回一个新对象
        // 对于不可变类，对构造器的每个可变参数进行保护性拷贝是必要的

        // 保护性拷贝是在检查参数有效性之前进行的，并且有效性检查时针对拷贝后的对象，而不是针对原始对象
        // 防止 从参数检查开始到拷贝参数之间(危险阶段 TOC-TOU攻击(time-of-check/time-of-use)) 其他线程进行修改

        // 对于参数类型可以被不可信任方子类化的参数，不要使用 clone 方法进行保护性拷贝
        // 返回可变内部域的保护性拷贝 (getter方法中重新复制一个返回)，可以使用 clone 方法
    }

    /**
     * 51. 谨慎设计方法签名
     */
    @Test
    public void test51() {

        // 谨慎的选择方法的名称
        // 不要过于追求提高便利的方法
        // 避免过长的参数列表(<=4)，相同类型的长参数序列有害
        //   1. 拆分成多个方法，提高方法的正交性
        //   2. 创建辅助类保存参数
        //   3. 从对象的构建到方法的调用都采用 Builder模式
        // 对于参数类型，优先使用接口而不是类
        // 对于 boolean 类型的参数，优先使用两个原始的枚举类型

    }

    /**
     * 52. 慎用重载
     */
    @Test
    public void test52() {
        // 调用哪个重载方法是在 编译 时做出决定的
        // 对于重载方法的选择是静态的，对于被覆盖方法的选择是动态的

        // ArrayList#remove 方法由两个重载
        // public E remove(int index) 一次指定位置的元素
        // public boolean remove(Object o) 移除指定元素

        // 不要再相同的参数位置调用带有不同函数接口的方法
        // new Thread(System.out::println).start();
        // ExecutorService service = Executors.newCachedThreadPool();
        // service.submit(System.out::println); // println 有多个重载，这里会有问题

        // String 两个重载方法
        // 让更具体化的重载方法把调用转发给更一般化的重载方法
        // public boolean contentEquals(StringBuffer sb)
        // public boolean contentEquals(CharSequence cs)

        // 需要避免同一组参数经过类型转换就可以被传递给不同的重载方法
        // 如果不能避免，需要包装传递同样的参数是，所有重载方法的行为保持一致
    }

    /**
     * 53. 慎用可变参数
     */
    @Test
    public void test53() {
        // 可变参数每次调用时都会导致一次数组的分配和初始化
    }

    /**
     * 第一个是指定类型的正常参数
     * 第二个是相同类型的可变参数
     */
    public int min(int first, int... remainingArgs) {
        int min = first;
        for (int arg : remainingArgs) {
            if (arg < min) {
                min = arg;
            }
        }
        return min;
    }

    /**
     * 54. 返回零长度的数组或集合，而不是 null
     * 55. 谨慎返回 Optional
     */
    @Test
    public void test54() {
        // 不要通过返回 Optional 的方法返回 null
        // Optional 本质上与受检异常类似

        String[] strings = {"a", "b", "c", "d"};
        Optional<String> max = Arrays.stream(strings).max(Comparator.naturalOrder());
        max.orElseThrow(IllegalArgumentException::new);

    }
}

class SetList {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();
        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }
        for (int i = 0; i < 3; i++) {
            set.remove(i);
            list.remove(((Integer) i)); // remove 方法有两个重载!!!
        }
        System.out.println("set = " + set);
        System.out.println("list = " + list);
    }
}