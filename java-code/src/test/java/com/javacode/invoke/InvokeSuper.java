package com.javacode.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * 通过 MethodHandle 调用父类的父类方法
 */
public class InvokeSuper {

    class GrandFather {
        public void thinking() {
            System.out.println("GrandFather thinking...");
        }
    }

    class Father extends GrandFather {
        public void thinking() {
            System.out.println("Father thinking...");
        }
    }

    class Son extends Father {
        public void thinking() {
            try {
                // MethodType mt = MethodType.methodType(void.class);
                // // jdk7 update9 之后为了保证 findSpecial 查找方法版本时受到约束(访问控制限制，参数类型限制)
                // MethodHandle mh = MethodHandles.lookup()
                //         .findSpecial(GrandFather.class, "thinking", mt, getClass());
                // mh.invoke(this);

                // 新版的 jdk 中，通过 allowedModes 参数控制
                // jdk9 中通过 module-info.java 限制了模块访问权限，这里还是有问题 (InaccessibleObjectException)
                MethodType mt = MethodType.methodType(void.class);
                Field implLookup = MethodHandles.Lookup.class
                        .getDeclaredField("IMPL_LOOKUP");
                implLookup.setAccessible(true);
                MethodHandle mh = ((MethodHandles.Lookup) implLookup.get(null))
                        .findSpecial(GrandFather.class, "thinking", mt, Father.class)
                        .bindTo(this);
                mh.invoke(this);

            } catch (Throwable ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new InvokeSuper().new Son().thinking();
    }
}
