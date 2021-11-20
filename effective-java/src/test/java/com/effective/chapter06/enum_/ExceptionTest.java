package com.effective.chapter06.enum_;

import java.lang.annotation.*;

/**
 * 一个可重复标记的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)
public @interface ExceptionTest {
    Class<? extends Exception> value();
}
