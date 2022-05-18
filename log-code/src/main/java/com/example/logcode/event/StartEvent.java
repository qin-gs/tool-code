package com.example.logcode.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 日志门面与日志实现
 * 日志框架主要是分为两大类： 日志门面+日志实现。将日志门面和日志实现分离其实是一种典型的门面模式
 * <p>
 * 日志门面
 * 日志门面定义了一组日志的接口规范，它并不提供底层具体的实现逻辑。Apache Commons Logging 和 Slf4j 就属于这一类。
 * <p>
 * 日志实现
 * 日志实现则是日志具体的实现，包括日志级别控制、日志打印格式、日志输出形式（输出到数据库、输出到文件、输出到控制台等）。
 * Log4j、Log4j2、Logback 以及 Java Util Logging 则属于这一类。
 */
@Component
public class StartEvent implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * 这是springboot默认的日志
     * <pre>
     *  org.slf4j.Logger;
     *  org.slf4j.LoggerFactory;
     *  Logger logger = LoggerFactory.getLogger(StartEvent.class);
     * </pre>
     */
    Logger logger = LoggerFactory.getLogger(StartEvent.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.debug("debug {}", 1);
        logger.info("info {} {} {}", 2, 3, 4);
        logger.warn("warn {}", 3);
        logger.error("error {}", 4);
    }
}
