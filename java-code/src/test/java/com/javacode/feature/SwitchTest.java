package com.javacode.feature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("jdk12 switch新特性")
public class SwitchTest {

    @Test
    public void test(Status status) {
        int result = 0;
        switch (status) {
            case OPEN:
                result = 1;
                break;
            case PROCESS:
                result = 2;
                break;
            case PENDING:
                result = 2;
                break;
            case CLOSE:
                result = 3;
                break;
            default:
                throw new RuntimeException("状态不正确");
        }
        System.out.println("result is " + result);

        // 箭头语法 ->，类似 Java 8 中的 Lambda 表达式；
        // 可以直接返回值给一个变量，并且可以不用 break 关键字；
        // case 条件，多个可以写在一行，用逗号分开；
        // 可以省略 break 关键字；
        var newResult = switch (status) {
            case OPEN -> 1;
            case PROCESS, PENDING -> 2;
            case CLOSE -> 3;
            default -> throw new RuntimeException("状态不正确");
        };
        System.out.println("result is " + newResult);
    }
}

enum Status {
    OPEN, INIT, PROCESS, PENDING, CLOSE;
}