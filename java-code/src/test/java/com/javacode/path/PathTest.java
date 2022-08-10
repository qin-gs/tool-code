package com.javacode.path;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class PathTest {

    @Test
    public void test() throws IOException {
        File f1 = new File("./demo.xml");
        File f2 = new File("F:\\IdeaProjects\\ToolCode\\java-code\\src\\test\\resources\\demo.xml");

        // getPath 得到构造 file 时候的路径
        System.out.println("f1.getPath() = " + f1.getPath());
        System.out.println("f2.getPath() = " + f2.getPath());

        // getAbsolutePath 得到全路径
        // 如果构造的时候是相对路径，返回当前目录的路径 + 构造 file 时候的路径
        // 如果构造的时候是全路径，直接返回
        System.out.println("f1.getAbsolutePath() = " + f1.getAbsolutePath());
        System.out.println("f2.getAbsolutePath() = " + f2.getAbsolutePath());

        // 全路径，处理 . 和 ..
        System.out.println("f1.getCanonicalPath() = " + f1.getCanonicalPath());
        System.out.println("f2.getCanonicalPath() = " + f2.getCanonicalPath());


    }
}
