package com.springcode.listener;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CustomizeUrlLoader extends URLClassLoader {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomizeUrlLoader.class);
    /**
     * 由 bootstrap 和 ext 加载的类 (这些包下的类不应该让自己的 jar 中重写)
     * <p>
     * (比如 activemq-all.5.11.1.jar 自带了 javax.annotation 下的类，其中的 Resource 注解和 rt.jar 中的相比缺少 lookup 字段，
     * 如果先加载了 activemq-all.5.11.1.jar 中的类会导致 spring 在处理 @Resource 注解时抛异常)
     * <p>
     * tomcat 中 WebappClassLoaderBase#loadClass(String, boolean) 方法
     * 先从本地缓存中加载，然后查看 jvm 是否加载过，
     */
    private final List<String> bootAndExtLoaderList = new ArrayList<>();

    {
        // 获取 boot 和 ext 加载的所有 class
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/load/application.properties"));
            String list = properties.getProperty("bootAndExtLoaderList");
            log.info("bootAndExtLoaderList: {}", list);
            if (list != null) {
                Arrays.stream(list.split(","))
                        .forEach(c -> bootAndExtLoaderList.add(c.trim()));
            }
        } catch (IOException e) {
            log.error("找不到 application.properties 文件", e);
            throw new RuntimeException(e);
        }
    }

    public CustomizeUrlLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {

                // 自己加载 (一些包不能自己加载 (java.lang.String)，估计无法禁止所有的)
                boolean anyMatch = bootAndExtLoaderList.stream().anyMatch(name::startsWith);
                if (!anyMatch) {
                    try {
                        c = findClass(name);
                    } catch (ClassNotFoundException ignored) {
                    }
                }

                // 自己没找到，父类加载
                // 这个地方的异常不能捕获
                if (c == null) {
                    c = this.getParent().loadClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    public static ClassLoader getUrlClassLoader(String classPath) {
        log.debug("加载 jarPath 目录 {}", classPath);
        URL[] urls = new URL[0];
        try {
            File file = new File(classPath);
            if (!file.isAbsolute()) {
                // 如果是相对路径，则转换为绝对路径 /webapps/项目名/WEB-INF/classes + classPath
                file = new File(CustomizeUrlLoader.class.getClassLoader().getResource("").getPath(), classPath);
            }

            if (!file.exists() || !file.isDirectory()) {
                throw new RuntimeException(file.getAbsolutePath() + " 不是目录");
            }

            // 获取该目录下所有的jar文件
            File[] jars = Arrays.stream(file.listFiles())
                    .filter(filePointer -> filePointer.getName().endsWith(".jar"))
                    .toArray(File[]::new);

            urls = new URL[jars.length];

            // 将其所有的路径转换为URL
            for (int i = 0; i < urls.length; i++) {
                File f = jars[i].getCanonicalFile();
                urls[i] = new URL("file:" + f.getAbsolutePath());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new CustomizeUrlLoader(urls, CustomizeUrlLoader.class.getClassLoader());
    }

}
