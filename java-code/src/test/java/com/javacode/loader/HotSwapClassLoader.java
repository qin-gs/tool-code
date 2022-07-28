package com.javacode.loader;

/**
 * 为了多次载入执行类加入的加载器。
 * 将 defineClass 方法暴露出来，只有外部显式调用的时候才会用到 loadByte 方法，
 * 由虚拟机调用时，仍按照原有的双亲委派规则使用 loadClass 方法进行类加载
 */
public class HotSwapClassLoader extends ClassLoader{

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class<?> loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }
}
