package com.heitian.ssm.test;

import java.util.Arrays;

/**
 * @version 1.0
 * @类名: searchArray.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-10-26
 */
public class searchArray {
    public static void main(String[] args) {
        int[] list = new int[1000];
        for (int i = 0; i < 1000; i++) {
            list[i] = i;
        }
        //String[] arr = new String[]{"CD", "BC", "EF", "DE", "AB"};
        int size = Arrays.binarySearch(list, 560);
        System.out.println(size);
    }
}
