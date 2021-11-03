package com.apache.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.comparator.CompositeFileComparator;
import org.apache.commons.io.comparator.DirectoryFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.commons.io.input.CountingInputStream;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@DisplayName("apache io 测试类")
public class IOTest {

    @Test
    public void ioTest() throws IOException {
        FileInputStream inputStream = new FileInputStream("");
        FileOutputStream outputStream = new FileOutputStream("");
        // 关闭多个流
        IOUtils.close(inputStream, outputStream);
    }

    /**
     * 输入流转换成byte数组
     */
    @Test
    public void byteTest() throws IOException {
        FileInputStream inputStream = new FileInputStream("");
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        byte[] result = outputStream.toByteArray();

        // common-io
        byte[] result2 = IOUtils.toByteArray(inputStream);
    }

    /**
     * 输入流转换成string
     */
    @Test
    public void stringTest() throws IOException {
        FileInputStream inputStream = new FileInputStream("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sBuilder.append(line);
        }
        String result = sBuilder.toString();

        // common-io
        String result2 = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        // 按行读取
        List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);

        // 将行写入输出流
        FileOutputStream outputStream = new FileOutputStream("");
        IOUtils.writeLines(lines, System.lineSeparator(), outputStream, StandardCharsets.UTF_8);

        // 复制输入流到输出流
        FileInputStream src = new FileInputStream("src.txt");
        FileOutputStream dest = new FileOutputStream("dest.txt");
        IOUtils.copy(src, dest);

    }

    /**
     * 文件读写
     */
    @Test
    public void fileTest() throws IOException {
        File file = new File("file.txt");
        // 读取文件到 字符串 数组 字节流
        String s = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        byte[] bytes = FileUtils.readFileToByteArray(file);

        // 将 字符串 数组 字符流 写入到文件
        File outFile = new File("out.txt");
        FileUtils.writeStringToFile(outFile, s, StandardCharsets.UTF_8);
        FileUtils.writeByteArrayToFile(outFile, bytes);
        FileUtils.writeLines(outFile, lines);

        // 文件复制
        File src = new File("src.txt");
        File dest = new File("dest.txt");
        FileUtils.moveFile(src, dest);
        FileUtils.copyFile(src, dest);

        // 复制文件到指定目录
        File srcDir = new File("./srcDir");
        File destDir = new File("./destDir");
        FileUtils.moveFileToDirectory(src, destDir, true);
        FileUtils.copyFileToDirectory(src, destDir);

        // 复制目录
        FileUtils.copyDirectory(srcDir, destDir);

        // 复制网络资源到文件
        FileUtils.copyURLToFile(new URL(""), dest);
        // 复制流到文件
        FileUtils.copyInputStreamToFile(new FileInputStream(""), dest);

        // 删除文件 目录
        FileUtils.delete(src);
        FileUtils.deleteDirectory(srcDir);
        // 计算文件 目录大小
        long l = FileUtils.sizeOf(src);

        // 递归获取目录下的所有文件(还可以限定文件后缀)
        Collection<File> files = FileUtils.listFiles(srcDir, null, true);
        // 获取jvm中的io临时mul
        File tempDirectory = FileUtils.getTempDirectory();

    }

    /**
     * 文件名
     */
    @Test
    public void fileNameTest() {
        String fileName = "/Users/qgs/IdeaProjects/tool-code/apache-code/src/test/java/com/apache/io/IOTest.java";
        String name = FilenameUtils.getName(fileName); // IOTest.java
        String baseName = FilenameUtils.getBaseName(fileName);// IOTest
        String extension = FilenameUtils.getExtension(fileName);// java
        String path = FilenameUtils.getPath(fileName); // /Users/qgs/IdeaProjects/tool-code/apache-code/src/test/java/com/apache/io/

        // 相对路径转绝对路径
        FilenameUtils.normalize(fileName);

    }

    @Test
    public void streamTest() throws IOException {
        // 可自动关闭的流
        AutoCloseInputStream stream = new AutoCloseInputStream(new FileInputStream(""));
        byte[] bytes = IOUtils.toByteArray(stream);

        // 倒序读文件
        ReversedLinesFileReader fileReader = new ReversedLinesFileReader(new File(""), StandardCharsets.UTF_8);
        List<String> lines = fileReader.readLines(5);

        // 获取读取的字节数
        CountingInputStream cis = new CountingInputStream(new FileInputStream(""));
        String s = IOUtils.toString(cis, StandardCharsets.UTF_8); // 读取到字符串
        long byteCount = cis.getByteCount(); // 读取的字节数

        // 可观察的流(边观察变读取，可以做字节替换)
        // ObservableInputStream
    }

    /**
     * 文件比较
     */
    @Test
    public void compareTest() {
        List<File> files = new ArrayList<>(Arrays.asList(new File("file1"), new File("file2")));
        files.sort(DirectoryFileComparator.DIRECTORY_COMPARATOR);
        // DefaultFileComparator：默认文件比较器，直接使用File的compare方法。（文件集合排序（ Collections.sort() ）时传此比较器和不传效果一样）
        // DirectoryFileComparator：目录排在文件之前
        // ExtensionFileComparator：扩展名比较器，按照文件的扩展名的ascii顺序排序，无扩展名的始终排在前面
        // LastModifiedFileComparator：按照文件的最后修改时间排序
        // NameFileComparator：按照文件名称排序
        // PathFileComparator：按照路径排序，父目录优先排在前面
        // SizeFileComparator：按照文件大小排序，小文件排在前面（目录会计算其总大小）
        // CompositeFileComparator：组合排序，将以上排序规则组合在一起

        // 组合排序
        CompositeFileComparator comparator = new CompositeFileComparator(DirectoryFileComparator.DIRECTORY_COMPARATOR, NameFileComparator.NAME_COMPARATOR);
        files.sort(comparator);
    }

    /**
     * 文件监听
     */
    @Test
    public void listenerTest() throws Exception {
        // 监听目录下的文件变化
        FileAlterationObserver observer = new FileAlterationObserver("/dir");
        observer.addListener(new ListenerDemo());
        FileAlterationMonitor monitor = new FileAlterationMonitor();
        monitor.addObserver(observer);
        monitor.start(); // 启动监视器
        // 主线程等待子线程执行完成
        Thread.currentThread().join(); // 防止主线程退出导致监视器退出
    }

    public static class ListenerDemo extends FileAlterationListenerAdaptor {
        @Override
        public void onFileCreate(File file) {
            System.out.println("有文件被创建");
        }
    }


}
