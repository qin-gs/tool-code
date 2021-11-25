package com.javacode.str;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("字符串相关的一些方法")
public class StringTest {

    /**
     * 一些字符串复制操作
     */
    @Test
    public void test() {
        String s = "abc";
        List<String> strings = Collections.nCopies(10, s);
        System.out.println(strings);

        String with = Strings.join(strings).with("+");
        System.out.println("with = " + with);

        String join = String.join("-", strings);
        System.out.println("join = " + join);

        String[] arr = new String[10];
        Arrays.fill(arr, s);
        System.out.println(Arrays.toString(arr));

        String repeat = s.repeat(10);
        System.out.println("repeat = " + repeat);
    }
}
