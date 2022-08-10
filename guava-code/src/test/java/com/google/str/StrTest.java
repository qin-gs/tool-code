package com.google.str;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class StrTest {

    @Test
    public void join() {
        // 跳过 null 或 用其他替换
        Joiner joiner = Joiner.on(", ").skipNulls();
        Joiner aNull = Joiner.on(" - ").useForNull("null");
        System.out.println(joiner.join("a", null, "b", "c"));
        System.out.println(aNull.join("a", null, "b", "c"));

    }

    @Test
    public void split() {
        // 尾部的最后一个空字符串会被忽略
        String[] split = ",a,,b,c,".split(",");
        System.out.println(Arrays.toString(split));

        ArrayList<String> list = Lists.newArrayList(Splitter.on(",")
                // 移除结果字符串的首尾字符
                .trimResults()
                // 忽略空字符串
                .omitEmptyStrings()
                // 限制拆分出的字符串数量
                .limit(2)
                .split("a,,b,c,"));
        System.out.println(list);
    }

    @Test
    public void matcher() {
        // trim 修剪，collapse 折叠，remove 移除，retain 保留
        String s = "a    b 10 c d e 2 f g h i 3    j k 4 l m n o p q    r s t 5 u v w    x y z 6 7 8    9";
        CharMatcher.javaIsoControl().removeFrom("\n\r\t");

        // 保留数字
        String retain = CharMatcher.digit().retainFrom(s);
        System.out.println("retain  = " + retain);
        String retain1 = CharMatcher.inRange('0', '9').retainFrom(s);
        System.out.println("retain1 = " + retain1);

        // 替换连续空格为一个空格
        String collapse = CharMatcher.whitespace().trimAndCollapseFrom(s, ' ');
        System.out.println("collapse = " + collapse);

        // 将数字替换成指定字符
        String replace = CharMatcher.javaDigit().replaceFrom(s, "*");
        System.out.println("replace = " + replace);

        // 只保留数字 和 小写字母
        String or = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(s);
        System.out.println("or = " + or);

        String remove = CharMatcher.inRange('a', 'z').removeFrom(s);
        System.out.println("remove = " + remove);

        // ANY: 匹配任何字符
        // ASCII: 匹配是否是ASCII字符
        // BREAKING_WHITESPACE: 匹配所有可换行的空白字符(不包括非换行空白字符,例如"\u00a0")
        // DIGIT: 匹配ASCII数字
        // INVISIBLE: 匹配所有看不见的字符
        // JAVA_DIGIT: 匹配UNICODE数字, 使用 Character.isDigit() 实现
        // JAVA_ISO_CONTROL: 匹配ISO控制字符, 使用 Character.isISOControl() 实现
        // JAVA_LETTER: 匹配字母, 使用 Character.isLetter() 实现
        // JAVA_LETTER_OR_DIGIT: 匹配数字或字母
        // JAVA_LOWER_CASE: 匹配小写
        // JAVA_UPPER_CASE: 匹配大写
        // NONE: 不匹配所有字符
        // SINGLE_WIDTH: 匹配单字宽字符, 如中文字就是双字宽
        // WHITESPACE: 匹配所有空白字符
    }
}
