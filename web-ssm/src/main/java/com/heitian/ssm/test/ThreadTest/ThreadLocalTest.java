package com.heitian.ssm.test.ThreadTest;

import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @类名: ThreadLocalTest.java
 * @描述: 线程本地ThreadLocal的介绍与使用:看出虽然多个线程对同一个变量进行访问，但是由于threadLocal变量由ThreadLocal
 * 修饰，则不同的线程访问的就是该线程设置的值，这里也就体现出来ThreadLocal的作用。
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class ThreadLocalTest {
    public static ThreadLocal<List<String>> listThreadLocal = new ThreadLocal<>();
    //public static List<String> listThreadLocal = new ArrayList<>();

    public void getThreadLocal() {
        System.out.println(Thread.currentThread().getName());
        //listThreadLocal.forEach(name -> System.out.println(name));
        listThreadLocal.get().forEach(name -> System.out.println(name));
    }

    public void setThreadLocal(List<String> values) {
        //listThreadLocal.addAll(values);
        listThreadLocal.set(values);
    }

    public static void main(String[] args) {
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        new Thread(() -> {
            List<String> list = Arrays.asList("张三", "李四", "王武");
            threadLocalTest.setThreadLocal(list);
            threadLocalTest.getThreadLocal();
        }).start();

        new Thread(() -> {
            //try {
            //Thread.sleep(1000);
            List<String> list = Arrays.asList("Chinese", "English");
            threadLocalTest.setThreadLocal(list);
            threadLocalTest.getThreadLocal();
            /*} catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }).start();
    }
}
