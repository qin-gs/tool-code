package com.javacode.annotated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AnnotatedElement 测试")
public class AnnotatedTest {

    /**
     * 这个接口（AnnotatedElement）的对象代表了在当前JVM中的一个“被注解元素”
     * （可以是Class，Method，Field，Constructor，Package等）。
     * <p>
     * 在Java语言中，所有实现了这个接口的“元素”都是可以“被注解的元素”。
     * 使用这个接口中声明的方法可以读取（通过Java的反射机制）“被注解元素”的注解。
     * 这个接口中的所有方法返回的注解都是不可变的、并且都是可序列化的。
     * 这个接口中所有方法返回的数组可以被调用者修改，而不会影响其返回给其他调用者的数组。
     * <p>
     * 子接口
     * <pre>
     *   AccessibleObject（可访问对象，如：方法、构造器、属性等）
     *   Class（类）
     *   Constructor（构造器，类的构造方法的类型）
     *   Executable（可执行的，如构造器和方法）
     *   Field（属性）
     *   Method（方法）
     *   Package（包）
     *   Parameter（参数，主要指方法或函数的参数，其实是这些参数的类型）
     * </pre>
     * <pre>
     * directly present：就是指直接修饰在某个元素上的注解；
     * indirectly present："间接修饰"注解就是指得容器注解(@ComponentScans({@CompaneScan(basePackages="")}))的数组中指定的注解；
     * present：并不是"直接修饰"注解和"间接修饰"注解的合集，而是"直接修饰"注解和父类继承下来的"直接注解"的合集；
     * associated："关联"是"直接修饰"注解、"间接修饰"注解以及父类继承下来的注解的合集；
     * </pre>
     * <p>
     * <pre>
     *   方法	                            directly-present    indirectly-present      present     associated
     *   getAnnotation	 	 	                                                          *
     *   getAnnotations	 	 	                                                          *
     *   getAnnotationsByType	 	 	 	                                                            *
     *   getDeclaredAnnotation	                *
     *   getDeclaredAnnotations	                *
     *   getDeclaredAnnotationsByType	        *	                *
     * </pre>
     */
    @Test
    public void test() {

    }

}
