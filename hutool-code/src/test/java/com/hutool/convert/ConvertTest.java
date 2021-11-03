package com.hutool.convert;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.lang.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("hutool convert 测试类")
public class ConvertTest {

    @Test
    public void test() {
        int a = 1;
        System.out.println(Convert.toStr(a));

        String s = "！@#￥%……&*（）——+【】；‘、，。/12";
        System.out.println(s);
        System.out.println(Convert.toSBC(s));
        System.out.println(Convert.toHex(s, StandardCharsets.UTF_8));

        long num = 1_2345_6789L;
        System.out.println(Convert.numberToWord(1_2345_6789));
        System.out.println(Convert.numberToChinese(num, false));

        List<String> strings = Arrays.asList("1", "2", "3", "4");
        System.out.println(Arrays.toString(Convert.toIntArray(strings)));
        System.out.println(Convert.toCollection(List.class, Integer.class, strings));
        System.out.println(Convert.toList(String.class, strings));

        // 转换成泛型
        Convert.convert(new TypeReference<List<String>>() {
        }, strings);

        String utf_8 = "unicode和字符串转换";
        String unicode = "unicode\\u548c\\u5b57\\u7b26\\u4e32\\u8f6c\\u6362";
        System.out.println(Convert.strToUnicode(utf_8));
        System.out.println(Convert.unicodeToStr(unicode));

        System.out.println(Convert.convertTime(num, TimeUnit.MILLISECONDS, TimeUnit.MINUTES));
        System.out.println(Convert.digitToChinese(num));

        System.out.println(Convert.numberToChinese(123.678, false));

        // 基本类型 -> 包装类型
        Class<?> wrap = Convert.wrap(int.class);
        System.out.println(wrap);
    }

    @Test
    public void converter() {
        int a = 1234;
        ConverterRegistry registry = ConverterRegistry.getInstance();
        Object convert = registry.convert(String.class, a);
        assertEquals("1234", convert);

        registry.putCustom(String.class, CustomConverter.class);
        System.out.println(java.util.Optional.ofNullable(registry.convert(String.class, a)).get());
    }

    /**
     * 自定义类型转换器
     */
    public static class CustomConverter implements Converter<String> {
        @Override
        public String convert(Object value, String defaultValue) throws IllegalArgumentException {
            return "custom " + value;
        }
    }
}
