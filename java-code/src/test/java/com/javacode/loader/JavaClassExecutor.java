package com.javacode.loader;

import java.lang.reflect.Method;

/**
 * java class 执行工具
 */
public class JavaClassExecutor {

    /**
     * 执行外部传过来的一个代表一个 java 类的 Byte 数组
     * 将输入的 byte 数组中代表 java.lang.System 的 CONSTANT_Class_info 的类名替换为劫持后的 HackSystem，
     * 执行方法为该类的 main 方法，输出结果为该类向 System.out/err 输出的信息
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modifiedBytes = cm.modifyUTF8Constant("java/lang/System", "com/javacode/loader/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class<?> clazz = loader.loadByte(modifiedBytes);
        try {
            Method mainMethod = clazz.getDeclaredMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[]{null});
        } catch (Exception e) {
            e.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferString();
    }
}
