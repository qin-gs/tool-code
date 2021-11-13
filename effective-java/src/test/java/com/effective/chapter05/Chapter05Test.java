package com.effective.chapter05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

@DisplayName("泛型 generic")
public class Chapter05Test {

    /**
     * 26. 不要使用原生类型
     */
    @Test
    public void test26() {

        // List<String>
        // 参数化类型: String
        // 原始类型: List

        // ? 通配符声明的集合不允许添加元素
        List<?> c = new ArrayList<>();

        // 必须使用原始类型的场景
        // 1. 获取 class 对象
        Class<List> clazz = List.class;
        // 2. instanceof
        if (c instanceof Set) {
            Set<?> s = (Set<?>) c;
        }
    }

    /**
     * 27. 消除未检查(unchecked)异常
     */
    public void test27() {
        ArrayList<String> list = new ArrayList<>();
        // 这个方法内部有一个 @SuppressWarnings("unchecked")
        // 该注解应在尽量小的作用域上使用，不要在类上声明!!!
        list.toArray(new String[0]);
    }

    /**
     * 28. 列表优先于数组
     * 29. 优先考虑泛型
     * 30. 优先使用泛型方法
     */
    @Test
    public void test28() {
        // Long[] 是 Object[] 的子类型
        Object[] longs = new Long[1];
        longs[0] = "a string in long[]"; // ArrayStoreException

        // List<Long> 不是 List<Object> 的子类型 (covariant 协变)
        // List<Object> list = new ArrayList<Long>();
        // List<String>[] 是不合法的
        // E, List<T>, List<String> 不可具化类型(non-reifiable type)
        // 由于泛型擦除的原因，运行时展示的信息比编译时的信息要少
        // List<?>, Map<?, ?> 可具化参数类型(无限制通配符)

        // 类型限制<E extends Comparable<E>>可以被读作“任意能与其自身进行比较的类型E”
    }

    /**
     * 31. 使用有限制通配符增加 API 灵活性
     */
    @Test
    public void test31() {
        // src 作为生产者，只能向外取
        // void pushAll(Iterable<? extends Number> src)
        // dest 作为消费者，只能往里放
        // void popAll(Collection<? super Integer> dest)

        // Comparable, Comparator 都是消费者
        // Comparable<? super T>
        // public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll)

        // 如果类型参数在方法声明中只出现一次，可以用通配符取代
        // public void swap(List<?> list, int i, int j)

        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        // 生产者
        Integer[] integers = {5, 6, 7};
        stack.pushAll(Arrays.stream(integers).toList());
        System.out.println(stack);

        // 消费者
        ArrayList<Number> dest = new ArrayList<>();
        stack.popAll(dest);
        System.out.println(dest);

    }

    /**
     * 32. 谨慎使用 泛型 和 可变参数
     */
    @Test
    public void test32() {
        // 将值保存在泛型可变参数数组里是不安全的
        // 当一个参数化类型的变量指向一个不是该类型的对象时，会产生堆污染(heap pollution)
        // 这会导致编辑器的自动生成转换失败，破坏泛型的基本保证

        // @SafeVarargs (只允许注解在 静态方法，final实例方法，私有实例方法中(因为没办法保证子类重写的方法也是安全的))
        // 该注解通过方法的设计者做出承诺，声明这是类型安全的(方法没有在数组中保存任何值，也不允许对数组的转义引用)
        // 也就是说，可变参数数组只是用来将数量可变的参数从调用程序传到方法，改方法就是安全的
        // Arrays#asList, Collections#addAll

        // 显式创建泛型数组时非法的，用泛型可变参数声明方法时可以的
        // 运行另一个方法访问一个 泛型可变参数数组 是不安全的

        List<String> strings = Arrays.asList("a", "b", "c");
        Collections.addAll(new ArrayList<>(), strings);

    }

    public void dangerous(List<String>... stringList) {
        List<Integer> integerList = List.of(123);
        Object[] objs = stringList;
        // 将 List<Integer> 放到了 List<String> 里面
        objs[0] = integerList;
        // stringList[0] 编译器会有一个自动的类型转换(Integer -> String)，会抛出 ClassCastException 异常
        String s = stringList[0].get(0);
        System.out.println(Arrays.toString(objs));
    }

    /**
     * 33. 优先使用类型安全的异构容器
     */
    @Test
    public void test33() {
        // 一个类的的字面被用在方法中，来表达编译时和运行时的类型信息时，被称为 类型令牌(type token)

    }

    public Annotation getAnnotation(AnnotatedElement element, String annotationTypeName) {
        Class<?> annotationType = null;
        try {
            annotationType = Class.forName(annotationTypeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return element.getAnnotation(annotationType.asSubclass(Annotation.class));
    }

}
