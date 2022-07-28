package com.javacode.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * MethodHandle 功能测试
 * <p>
 * Reflection：模拟 java 代码层次的方法调用 invokestatic, invokevirtual, invokespecial
 * MethodHandle：模拟字节码层次的方法调用   findStatic, findVirtual, findSpecial
 * <p>
 * invokestatic：调用静态方法
 * invokespecial：调用实例构造函数，私有方法，父类方法
 * invokeinterface：调用接口方法，在运行时再确定一个实现此接口的对象
 * invokevirtual：调用虚方法 (除了三种之外的)
 * invokedynamic：调用动态方法
 */
public class MethodHandleTest {

    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object o = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        // 无论 o 是哪种类型，都可以调用 println 方法
        getPrintMethodHandle(o).invokeExact("Hello, MethodHandle!");
    }

    private static MethodHandle getPrintMethodHandle(Object o) throws Exception {
        // 方法类型：(Ljava/lang/String;)V 返回值类型：void；参数类型：String
        MethodType mt = MethodType.methodType(void.class, String.class);
        // 在指定类中查找符合给的方法名称，方法类型，且符合调用权限的方法句柄
        // 调用虚方法，第一个参数是隐式的，代表方法的接收者(this)
        // 以前在参数列表中进行传递，现在通过 bindTo 完成
        return MethodHandles.lookup()
                .findVirtual(o.getClass(), "println", mt)
                .bindTo(o);
    }
}
