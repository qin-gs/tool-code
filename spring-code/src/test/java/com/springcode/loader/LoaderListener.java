package com.springcode.loader;

import com.springcode.listener.CustomizeUrlLoader;
import org.apache.tomcat.util.descriptor.web.*;
import org.slf4j.Logger;
import org.xml.sax.InputSource;

import javax.servlet.*;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

import static java.util.Comparator.comparing;

/**
 * 通过监听器是无法完成手动加载 servlet 的，这种方式只是把 servlet 创建好了交给 tomcat，它的初始化函数 (init) 调用时机不由我们控制，初始化过程中无法修改类加载器导致启动失败
 * <p>
 * spring 的启动过程中，ConfigurationClassParser 在解析 @Import 注解时，会判断 AutoProxyRegistrar 和 ImportBeanDefinitionRegistrar 之间是否存在继承关系，
 * 如果不存在会抛出异常 ( AutoProxyRegistrar  是由创建的 servlet 的类加载器加载的，
 * ImportBeanDefinitionRegistrar 是由 Thread.currentThread().getContextClassLoader() 加载的，如果两者不一样就不存在继承关系)
 */
public class LoaderListener implements ServletContextListener {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        WebXml[] webXmls = getWebXmls();

        // 记录一下当前的类加载器
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        for (WebXml webXml : webXmls) {
            // 获取 jarPath
            String jarPath = webXml.getContextParams().get("jarPath");
            // 每次都创建一个类加载器
            ClassLoader classLoader = CustomizeUrlLoader.getUrlClassLoader(jarPath);
            // 这个必须要配置，否则后面 spring 的启动会报错
            Thread.currentThread().setContextClassLoader(classLoader);

            // 添加 listener, filter, servlet
            addListener(webXml.getListeners(), context, classLoader);
            addFilter(webXml.getFilters(), webXml.getFilterMappings(), context, classLoader);
            addServlet(webXml.getServlets(), webXml.getServletMappings(), context, classLoader);
        }
        Thread.currentThread().setContextClassLoader(currentClassLoader);
        log.debug("加入 listener, filter, servlet 成功");

    }

    /**
     * 读取 /resources/web/*.xml 文件，封装成 WebXml
     */
    private WebXml[] getWebXmls() {

        try {
            // 获取目录下所有的 xml 文件，如果目录不存在会出现异常
            File[] xmlFiles = Arrays.stream(Objects.requireNonNull(new File(this.getClass().getClassLoader().getResource("").getPath() + "web")
                            .listFiles(file -> file.getName().endsWith(".xml"))))
                    .sorted(comparing(File::getName))
                    .toArray(File[]::new);

            WebXml[] webXmls = new WebXml[xmlFiles.length];
            for (int i = 0; i < xmlFiles.length; i++) {
                File xmlFile = xmlFiles[i];
                // 获取 web.xml 中的配置
                WebXmlParser webXmlParser = new WebXmlParser(false, false, false);
                WebXml webXml = new WebXml();
                boolean parseWebXml = webXmlParser.parseWebXml(new InputSource(Files.newInputStream(xmlFile.toPath())), webXml, false);
                if (!parseWebXml) {
                    log.error("解析 {} 失败", xmlFile.getName());
                }
                webXmls[i] = webXml;
            }
            return webXmls;
        } catch (Exception e) {
            log.error("加载 web 目录下的 xml 文件失败", e);
        }
        return new WebXml[0];
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    /**
     * 给 servletContext 添加 servlet
     */
    private void addServlet(Map<String, ServletDef> servlets, Map<String, String> servletMappings, ServletContext context, ClassLoader classLoader) {
        if (servlets == null || servlets.size() == 0) {
            return;
        }
        for (Map.Entry<String, ServletDef> entry : servlets.entrySet()) {
            String servletName = entry.getKey();
            ServletDef servletDef = entry.getValue();
            try {
                @SuppressWarnings("unchecked")
                Class<? extends Servlet> aClass = (Class<? extends Servlet>) classLoader.loadClass(servletDef.getServletClass());
                // 不能使用 createServlet() 创建，因为这个方法 newInstance 使用的类加载器不是当前线程的类加载器 (org.apache.catalina.core.DefaultInstanceManager.classLoader)
                ServletRegistration.Dynamic servlet = context.addServlet(servletName, aClass.newInstance());
                if (servlet == null) {
                    log.error("添加 {} 失败，可能已存在了", servletName);
                    continue;
                }
                servlet.addMapping(getPathByServletName(servletMappings, servletName));

                if (servletDef.getLoadOnStartup() != null) {
                    servlet.setLoadOnStartup(servletDef.getLoadOnStartup());
                }
                if (servletDef.getParameterMap() != null) {
                    servlet.setInitParameters(servletDef.getParameterMap());
                }
                if (servletDef.getEnabled() != null) {
                    // enabled 这个参数好像没有对应的
                }
                if (servletDef.getAsyncSupported() != null) {
                    servlet.setAsyncSupported(servletDef.getAsyncSupported());
                }
                if (servletDef.getMultipartDef() != null) {
                    String maxFileSize = servletDef.getMultipartDef().getMaxFileSize();
                    String maxRequestSize = servletDef.getMultipartDef().getMaxRequestSize();
                    String fileSizeThreshold = servletDef.getMultipartDef().getFileSizeThreshold();
                    MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                            servletDef.getMultipartDef().getLocation(),
                            maxFileSize == null ? -1L : Long.parseLong(maxFileSize),
                            maxRequestSize == null ? -1L : Long.parseLong(maxRequestSize),
                            fileSizeThreshold == null ? 0 : Integer.parseInt(fileSizeThreshold)
                    );
                    servlet.setMultipartConfig(multipartConfigElement);
                }
                if (servletDef.getRunAs() != null) {
                    servlet.setRunAsRole(servletDef.getRunAs());
                }
                log.info("添加 servlet {} 成功", servletName);
            } catch (Exception e) {
                log.error("添加 {} servlet 失败", servletName, e);
            }
        }
    }

    /**
     * 给 servletContext 添加 filter
     */
    private void addFilter(Map<String, FilterDef> filters, Set<FilterMap> filterMappings, ServletContext context, ClassLoader classLoader) {
        if (filters == null || filters.size() == 0) {
            return;
        }
        for (Map.Entry<String, FilterDef> entry : filters.entrySet()) {
            String filterName = entry.getKey();
            FilterDef filterDef = entry.getValue();
            try {
                @SuppressWarnings("unchecked")
                Class<? extends javax.servlet.Filter> aClass = (Class<? extends javax.servlet.Filter>) classLoader.loadClass(filterDef.getFilterClass());
                FilterRegistration.Dynamic filter = context.addFilter(filterName, aClass.newInstance());
                if (filterDef.getAsyncSupported() != null) {
                    filter.setAsyncSupported(Boolean.parseBoolean(filterDef.getAsyncSupported()));
                }
                filter.setInitParameters(filterDef.getParameterMap());

                for (FilterMap filterMapping : filterMappings) {
                    if (filterMapping.getFilterName().equals(filterName)) {
                        filter.addMappingForUrlPatterns(null, false, filterMapping.getURLPatterns());
                        filter.addMappingForServletNames(null, false, filterMapping.getServletNames());
                    }
                }
                log.info("添加 filter {} 成功", filterName);
            } catch (Exception e) {
                log.error("添加 {} filter 失败", filterName, e);
            }
        }
    }

    /**
     * 给 servletContext 添加 listener
     */
    private void addListener(Set<String> listeners, ServletContext context, ClassLoader classLoader) {
        if (listeners == null || listeners.size() == 0) {
            return;
        }
        for (String listener : listeners) {
            try {
                @SuppressWarnings("unchecked")
                Class<? extends EventListener> aClass = (Class<? extends EventListener>) classLoader.loadClass(listener);
                // 这里如果还有 ServletContextListener 会抛异常
                context.addListener(aClass.newInstance());
                log.info("添加 listener {} 成功", listener);
            } catch (Exception e) {
                log.error("添加 {} listener 失败", listener, e);
            }
        }
    }

    /**
     * 根据 servlet 名称获取对应的 servlet-mapping
     */
    private String getPathByServletName(Map<String, String> servletMappings, String servletName) {
        for (Map.Entry<String, String> entry : servletMappings.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value.equals(servletName)) {
                return key;
            }
        }
        log.error("没有找到 {} 对应的 servlet-mapping path", servletName);
        // 没找到给一个默认的
        return "/" + servletName;
    }

}
