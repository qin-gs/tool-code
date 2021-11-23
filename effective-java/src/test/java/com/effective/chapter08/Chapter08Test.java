package com.effective.chapter08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@DisplayName("方法")
public class Chapter08Test {

    /**
     * 49. 检查参数的有效性
     */
    @Test
    public void test49() {
        String arg = "";
        // 进行参数检查
        Objects.requireNonNull(arg, "参数不不能为空");
        Objects.checkFromIndexSize(2, 3, 1);
        Objects.checkFromToIndex(2, 4, 2);
        Objects.checkIndex(2, 4);
    }
}
