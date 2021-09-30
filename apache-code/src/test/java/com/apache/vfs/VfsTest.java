package com.apache.vfs;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.RandomAccessContent;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.util.RandomAccessMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@DisplayName("apache vfs 测试类")
public class VfsTest {

    private final String filePath = "C:/Users/qgs/Desktop/something.txt";
    private final String folderPath = "C:/Users/qgs/Desktop/something";
    private final String jarPath = "D:/.m2/repository/org/apache/commons/commons-vfs2/2.9.0/commons-vfs2-2.9.0.jar";
    private final FileSystemManager manager = VFS.getManager();
    private FileObject fo;

    public VfsTest() throws FileSystemException {
    }

    @AfterEach
    public void afterEach() throws FileSystemException {
        if (fo != null) {
            fo.close();
        }
        if (manager != null) {
            manager.close();
        }
    }

    /**
     * 获取文件属性
     */
    @Test
    public void fileProperty() throws FileSystemException {
        fo = manager.resolveFile(folderPath);
        System.out.println(fo.getFileOperations());
        System.out.println(fo.getFileSystem());
        if (!fo.exists()) {
            return;
        }
        System.out.println("fo.getParent() = " + fo.getParent());
        System.out.println("fo.getName() = " + fo.getName());
        System.out.println("fo.getPath() = " + fo.getPath());
        System.out.println("fo.getPublicURIString() = " + fo.getPublicURIString());
        System.out.println("fo.getURI() = " + fo.getURI());
        System.out.println("fo.getURL() = " + fo.getURL());
        System.out.println("fo.isFile() = " + fo.isFile());
        System.out.println("fo.isFolder() = " + fo.isFolder());

        System.out.println("fo.isSymbolicLink() = " + fo.isSymbolicLink());
        System.out.println("fo.isExecutable() = " + fo.isExecutable());
        System.out.println("fo.isHidden() = " + fo.isHidden());
        System.out.println("fo.getType() = " + fo.getType());

    }

    /**
     * TODO jar的文件属性 获取不到
     */
    @Test
    public void jarProperties() throws FileSystemException {
        fo = manager.resolveFile(jarPath);
        if (fo.isFile()) {
            FileContent content = fo.getContent();
            Map<String, Object> attributes = content.getAttributes();
            System.out.println(attributes);
        }
    }

    /**
     * 文件内容(仅支持jar和hdfs)
     */
    @Test
    public void fileContent() throws IOException {
        fo = manager.resolveFile(filePath);
        if (fo.isFile()) {
            FileContent content = fo.getContent();
            String s = content.getString(StandardCharsets.UTF_8);
            System.out.println(s);
        }
    }

    /**
     * 设置文件权限(仅支持本地和sftp文件)
     */
    @Test
    public void setMode() throws FileSystemException {
        fo = manager.resolveFile(filePath);
        if (fo.isFile()) {
            fo.setWritable(true, true);
            fo.setExecutable(true, true);
        }
    }

    /**
     * 读取目录
     */
    @Test
    public void directoryTest() throws FileSystemException {
        fo = manager.resolveFile(folderPath);
        if (fo.isFolder()) {
            // 只获取当前目录下的文件和文件夹
            FileObject[] children = fo.getChildren();
            // 根据文件名获取文件
            FileObject child = fo.getChild("tmp.txt");
            // 递归获取目录下所有文件
            FileObject[] files = fo.findFiles(Selectors.SELECT_FILES);

            System.out.println(Arrays.toString(children));
            System.out.println(child);
            System.out.println(Arrays.toString(files));
        }
    }

    /**
     * 删除文件(支持本地文件、内存文件、FTP、SFTP、HDFS)
     */
    @Test
    public void deleteFile() throws FileSystemException {
        String f = "C:/Users/qgs/Desktop/v2rayN-Core - 副本";
        fo = manager.resolveFile(f);
        if (fo.isFolder()) {
            int i = fo.deleteAll(); // 删除所有文件，返回删除数量
            System.out.println(i);
            // fo.delete(Selectors.EXCLUDE_SELF); // 不删除自己
            // fo.delete(Selectors.SELECT_FOLDERS); // 删除目录下的空文件夹，如果文件夹下有文件不删除
            // fo.delete(Selectors.SELECT_CHILDREN); // 删除当前目录下的文件，不递归删除
            // fo.delete(Selectors.SELECT_FILES); // 递归删除目录下的文件，只留下目录
            // fo.delete(Selectors.SELECT_SELF); // 删除目录本身，如果包含子文件则删除失败返回0
        } else if (fo.isFile()) {
            fo.delete(); // 删除文件本身
        }
    }

    /**
     * 复制文件
     */
    @Test
    public void copyFile() throws IOException {
        fo = manager.resolveFile(filePath);
        if (fo.isFile()) {
            FileContent content = fo.getContent();
            content.write(new FileOutputStream("E:/something.txt"));
        }
    }

    /**
     * 修改文件
     * 新增，修改内容: 本地文件、内存文件、FTP、SFTP、GZip、bz2、HDFS
     * 追加：本地文件、内存文件、FTP、SFTP
     * 覆盖：GZip、bz2、HDFS
     */
    @Test
    public void modifyFile() throws IOException {
        fo = manager.resolveFile("E:/modify.txt");
        fo.createFile();
        FileContent content = fo.getContent();
        OutputStream os;
        if (fo.isWriteable()) {
            os = content.getOutputStream();
            // 覆盖写入
            IOUtils.write("测试", os, StandardCharsets.UTF_8);
            os.close();

            os = content.getOutputStream(true);
            IOUtils.write("追加数据", os, StandardCharsets.UTF_8);
            os.close();
        }
    }

    /**
     * 随机读写
     * 随机只读：本地文件、内存文件、FTP、SFTP、HDFS、HTTP
     * 随机读写：本地文件、内存文件
     */
    @Test
    public void randomFile() throws FileSystemException {
        fo = manager.resolveFile(filePath);
        FileContent content = fo.getContent();
        RandomAccessContent rac = content.getRandomAccessContent(RandomAccessMode.READ);
    }

    /**
     * 文件监听
     */
    @Test
    public void monitorFile() throws FileSystemException {
        String path = "E:/something.txt";
        fo = manager.resolveFile(path);
        fo.getFileSystem().addListener(fo, new FileListener() {
            @Override
            public void fileCreated(FileChangeEvent event) throws Exception {
                System.out.println("文件创建: " + event.getFileObject().getName());
            }

            @Override
            public void fileDeleted(FileChangeEvent event) throws Exception {
                System.out.println("文件删除: " + event.getFileObject().getName());
            }

            @Override
            public void fileChanged(FileChangeEvent event) throws Exception {
                System.out.println("文件修改: " + event.getFileObject().getName());
            }
        });
        if (!fo.exists()) {
            fo.createFile();
        }
        fo.setWritable(false, false);
        fo.delete();
    }

}
