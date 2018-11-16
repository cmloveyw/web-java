package com.heitian.ssm.test;

import java.lang.reflect.Field;

/**
 * @version 1.0
 * @类名: IntegerTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-10-29
 */
public class IntegerTest {
    /*public static void main(String[] args) {
        int a = 100;
        int b = 129;
        int c = 129;
        System.out.println("ccc" + (b == c));
        System.out.println("ccc" + (a == b));
    }*/

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Class cache = Integer.class.getDeclaredClasses()[0]; //1
        Field myCache = cache.getDeclaredField("cache"); //2
        myCache.setAccessible(true);//3

        Integer[] newCache = (Integer[]) myCache.get(cache); //4
        newCache[132] = newCache[133]; //5

        int a = 2;
        int b = a + a;
        System.out.printf("%d + %d = %d", a, a, b); //打印时由于做了装箱，int数据变成了Integer，这时会采用缓存，所以4就会打印出5来。
        System.out.println(b);
    }
}
