package com.heitian.ssm.test.T;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @version 1.0
 * @类名: Test.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-22
 */
public class Test {
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = Test.class.getMethod("main", String[].class);
        for (final Parameter parameter : method.getParameters()) {
            System.out.println("Parameter:" + parameter.getName());
        }
    }
}
