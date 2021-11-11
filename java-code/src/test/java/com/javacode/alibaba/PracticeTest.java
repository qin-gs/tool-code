package com.javacode.alibaba;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("alibaba 开发手册 测试")
public class PracticeTest {

    @Test
    public void test() {
        switchString(null);
    }

    public void switchString(String s) {
        switch (s) {
            case "first":
            case "second":
            default:{
                System.out.println(s);
            }
        }
    }
}
