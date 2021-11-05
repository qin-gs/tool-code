package com.codecomplete.chapter18;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Consumer;

@DisplayName("表驱动方法")
public class TableTest {

    /**
     * 直接访问法
     */
    @Test
    public void access() {

        // 获取一个月中的天数，将值存入数组，加上闰年判断
        int[][] days = {
                {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
                {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
        };
        // 创建三维数组，填充不同年龄不同性别是否抽烟对应的花费
        // 可以直接从文件中读出来赋值
        int maxAge = 100, gender = 2, smokeStatus = 2;
        int[][][] costs = new int[maxAge][gender][smokeStatus];
        int userAge = 34;
        // 如果70岁以上的人一个花费，17岁以下的人一个花费，使用 max, min 处理
        // 复杂是将 键值的获取提取成独立的子程序
        System.out.println(costs[Math.max(Math.min(70, userAge), 17)][0][0]);

        Map<String, Consumer<String>> map = Map.of(
                "A", System.out::println,
                "B", x -> System.out.println(x + " B")
        );
        map.get("A").accept("test 表驱动法");
    }

    /**
     * 索引访问法
     */
    @Test
    public void index() {
        // 用一张表存储索引，拿到主键后去主表中查详细数据
    }

    /**
     * 阶梯访问表
     */
    @Test
    public void stair() {
        // 根据成绩评定等级
        char grade = getGrade(99);
        System.out.println(grade);
    }

    public char getGrade(int studentScore) {
        int level = 0;
        char grade = 'A'; // 这里声明成最高等级
        int[] rangeLimit = {50, 65, 75, 90, 100}; // 这里是每个等级的上限
        char[] grades = {'E', 'D', 'C', 'B', 'A'};
        while (level < grades.length - 1) { // 这里使用 length - 1 如果是最高等级可以少进行一次循环(上面已经赋值好了)
            if (studentScore < rangeLimit[level]) { // 注意端点问题
                grade = grades[level];
                break; // 如果找到了直接跳出去
            }
            level = level + 1; // 从低到高判断，如果不符合依次向上查询
        }
        return grade;
    }


}
