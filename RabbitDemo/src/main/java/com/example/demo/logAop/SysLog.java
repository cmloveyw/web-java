package com.example.demo.logAop;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @类名: SysLog.java
 * @描述: 定义日志注解
 * @创建人: CM
 * @创建时间: 2019-1-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
