package com.javacode.collector;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@DisplayName("Collector 类测试")
public class CollectorTest {

    public static List<Student> students;

    @BeforeEach
    public void beforeEach() {
        students = List.of(
                new Student("aa", 721, true, Student.GradeType.THREE),
                new Student("bb", 637, true, Student.GradeType.THREE),
                new Student("cc", 666, true, Student.GradeType.TWO),
                new Student("dd", 531, true, Student.GradeType.TWO),
                new Student("ee", 483, false, Student.GradeType.THREE),
                new Student("ff", 367, true, Student.GradeType.THREE),
                new Student("gg", 499, false, Student.GradeType.ONE)
        );
    }


    @Test
    public void test1() {
        // 如果任何记录的值是NaN或者总和在任何点NaN，那么平均值将是NaN
        Double collect = students.stream().collect(Collectors.averagingDouble(Student::getTotalScore));
        Optional.of(collect).ifPresent(System.out::println);

        // 生成一个不可变列表
        List<Student> list = students.stream().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        list.forEach(System.out::println);

        Optional.ofNullable(
                students.stream().collect(Collectors.collectingAndThen(
                        Collectors.averagingInt(Student::getTotalScore),
                        x -> "average score is " + x))
        ).ifPresent(System.out::println);

        Optional.of(
                students.stream().collect(Collectors.counting())
        ).ifPresent(System.out::println);

    }

    @Test
    public void groupBy() {
        Map<Student.GradeType, List<Student>> collect = students.stream().collect(Collectors.groupingBy(Student::getGradeType));
        Optional.of(collect).ifPresent(System.out::println);

        // 根据分类函数对元素进行分组，然后使用指定的下游Collector对与给定键关联的值执行归约操作。
        Map<Student.GradeType, Long> map = students.stream().collect(Collectors.groupingBy(Student::getGradeType, Collectors.counting()));
        Optional.of(map).ifPresent(System.out::println);

        TreeMap<Student.GradeType, Double> treeMap = students.stream().collect(Collectors.groupingBy(
                Student::getGradeType,
                TreeMap::new,
                Collectors.averagingInt(Student::getTotalScore)
        ));
        Optional.of(treeMap).ifPresent(System.out::println);
    }

    @Test
    public void join() {
        Optional.of(
                students.stream().map(Student::getName)
                        .collect(Collectors.joining("-", "start-- ", " --end"))
        ).ifPresent(System.out::println);
    }

    @Test
    public void map() {
        Optional.of(
                students.stream().collect(Collectors.mapping(Student::getName, Collectors.joining(",")))
        ).ifPresent(System.out::println);
    }

    @Test
    public void minMax() {
        Optional.of(
                students.stream().collect(Collectors.maxBy(Comparator.comparingInt(Student::getTotalScore)))
        ).ifPresent(System.out::println);
        Optional.of(
                students.stream().collect(Collectors.minBy(Comparator.comparingInt(Student::getTotalScore)))
        ).ifPresent(System.out::println);
    }

    @Test
    public void partition() {
        Optional.of(students.stream().collect(
                Collectors.partitioningBy(Student::isLocal)
        )).ifPresent(System.out::println);

        Optional.of(
                students.stream().collect(
                        Collectors.partitioningBy(
                                Student::isLocal,
                                Collectors.averagingInt(Student::getTotalScore)
                        )
                )
        ).ifPresent(System.out::println);
    }

    @Test
    public void reduce() {
        Optional.of(
                students.stream().reduce(BinaryOperator.maxBy(Comparator.comparingInt(Student::getTotalScore)))
        ).ifPresent(System.out::println);

        Optional.of(
                students.stream().map(Student::getTotalScore).reduce(0, Integer::sum)
        ).ifPresent(System.out::println);

        Optional.of(
                students.stream().collect(
                        Collectors.reducing(0, Student::getTotalScore, Integer::sum)
                )
        ).ifPresent(System.out::println);
    }

    @Test
    public void summarize() {
        // 返回统计结果
        DoubleSummaryStatistics collect = students.stream().collect(Collectors.summarizingDouble(Student::getTotalScore));
        Optional.of(collect).ifPresent(System.out::println);
    }

    @Test
    public void sum() {
        Double collect = students.stream().collect(
                Collectors.summingDouble(Student::getTotalScore)
        );
        System.out.println(collect);
    }

    @Test
    public void collect() {
        LinkedList<Student> collect = students.stream().filter(x -> x.getTotalScore() > 600)
                .collect(Collectors.toCollection(LinkedList::new));
        Optional.of(collect).ifPresent(x -> System.out.println(x.getClass() + " " + x));

        ConcurrentMap<String, Integer> concurrentMap = students.stream().collect(Collectors.toConcurrentMap(Student::getName, Student::getTotalScore));
        Optional.of(concurrentMap).ifPresent(System.out::println);

        ConcurrentMap<String, Integer> map = students.stream().collect(Collectors.toConcurrentMap(Student::getName, Student::getTotalScore, (x, y) -> x + y));
        System.out.println(map);

        // 统计每个年级人的数量
        ConcurrentSkipListMap<Student.GradeType, Integer> skipListMap = students.stream().collect(
                Collectors.toConcurrentMap(
                        Student::getGradeType,
                        x -> 1,
                        Integer::sum,
                        ConcurrentSkipListMap::new
                )
        );
        System.out.println(skipListMap);
    }

    @Test
    public void toList() {
        List<Student> studentList = students.stream().filter(Student::isLocal)
                .collect(Collectors.toList());
        System.out.println(studentList);
    }

    @Test
    public void toMap() {

        // 如果有重复值会抛异常
        // ConcurrentMap<Student.GradeType, String> concurrentMap = students.stream().collect(
        //         Collectors.toConcurrentMap(Student::getGradeType, Student::getName)
        // );
        // System.out.println(concurrentMap);

        // 如果有重复值需要用这个构造函数
        ConcurrentMap<Student.GradeType, String> collect = students.stream().collect(
                Collectors.toConcurrentMap(Student::getGradeType, Student::getName, StringUtils::join)
        );
        System.out.println(collect);

        ConcurrentHashMap<Student.GradeType, String> map = students.stream().collect(
                Collectors.toConcurrentMap(Student::getGradeType, Student::getName, StringUtils::join, ConcurrentHashMap::new)
        );
        System.out.println(map);
    }

    @Test
    public void toSet() {
        Set<Student> collect = students.stream().filter(Student::isLocal).collect(Collectors.toSet());
        System.out.println(collect);
    }
}

