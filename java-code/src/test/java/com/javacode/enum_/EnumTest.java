package com.javacode.enum_;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;

@DisplayName("EnumSet, EnumMap 方法测试")
public class EnumTest {

    @Test
    public void enumSetTest() {
        EnumMap<Color, String> map = new EnumMap<>(Color.class);
        map.put(Color.BLACK, "黑色");
        map.put(Color.WHITE, "白色");
        System.out.println(map);

        EnumSet<Color> colors = EnumSet.of(Color.BLUE, Color.RED);
        System.out.println(colors);

        // 创造一个有全部元素的集合
        EnumSet<Color> allColors = EnumSet.allOf(Color.class);
        System.out.println(allColors);

        EnumSet<Color> noneColor = EnumSet.noneOf(Color.class);
        System.out.println(noneColor);

        // 创造一个指定范围元素的集合
        EnumSet<Color> rangeColor = EnumSet.range(Color.RED, Color.BLACK);
        System.out.println(rangeColor);

        // 补集
        EnumSet<Color> complementColor = EnumSet.complementOf(rangeColor);
        System.out.println(complementColor);

        EnumSet<Color> copyColor = EnumSet.copyOf(Arrays.asList(Color.YELLOW, Color.BLACK));
        System.out.println(copyColor);
    }

    @Test
    public void enumMapTest() {

    }

}


enum Color {
    YELLOW, RED, BLUE, BLACK, WHITE
}