package com.effective.chapter10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("异常")
public class Chapter10Test {

    /**
     * 69. 只针对异常的情况才使用异常
     * 70. 对于可恢复的情况使用受检异常，对编程错误使用运行时异常
     * 71. 避免不必要的使用受检异常
     * 72. 优先使用标准异常
     */
    @Test
    public void test69() {
        // 异常应该只用于异常的情况下，不应该用于正常的控制流
        // 设计良好的api不应该强迫客户端为了正常的控制流使用异常

        // 如果希望调用者能够适当的恢复，就应该使用受检异常
        // 用运行时异常表明编程错误
        // 不要实现任何新的Error子类，自己实现的所有受检异常都应该是 RuntimeException 的子类
    }
}
