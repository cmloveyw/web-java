package com.heitian.ssm.test;

import com.google.common.base.Strings;

/**
 * @version 1.0
 * @类名: StringTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-7-25
 */
public class StringTest {
    public static void main(String[] args) {
        int minLength = 5;
        String aa = Strings.padStart("99999", minLength, '0');
        String bb = Strings.padEnd("123", minLength, '0');
        System.out.println("padStartString：" + aa);
        System.out.println("padEndString：" + bb);
    }

}
