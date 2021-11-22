package com.effective.chapter07;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Anagrams {

    public void test(String fileName, int size) throws IOException {
        File file = new File(fileName);

        HashMap<String, Set<String>> map = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                // 如果存在返回key对应的值
                // 如果不存在将第二个参数的返回值存入并返回
                // 返回后add进去
                map.computeIfAbsent(
                                alphabetize(word),
                                (unused) -> new HashSet<>()
                        )
                        .add(word);
            }
        }
        for (Set<String> value : map.values()) {
            System.out.println(value);
        }

        // Stream ------
        try (final Stream<String> lines = Files.lines(Paths.get(fileName))) {
            lines.collect(groupingBy(
                                    word -> word.chars().sorted()
                                            .collect(
                                                    StringBuilder::new,
                                                    (sb, c) -> sb.append(((char) c)),
                                                    StringBuilder::append
                                            )
                                            .toString()
                            )
                    )
                    .values().stream()
                    .filter(word -> word.size() > size)
                    .map(group -> group.size() + ": " + group)
                    .forEach(System.out::println);
        }

    }

    private String alphabetize(String s) {
        final char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {
        String[] strings = {"abc", "bcd", "bca", "cav"};
        final Map<String, List<String>> collect = Arrays.stream(strings).collect(groupingBy(
                        word -> word.chars().sorted()
                                .collect(
                                        StringBuilder::new,
                                        (sb, c) -> sb.append(((char) c)),
                                        StringBuilder::append
                                )
                                .toString()
                )
        );
        System.out.println(collect);
    }
}
