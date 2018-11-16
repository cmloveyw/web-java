package com.heitian.ssm.test.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @类名: ThreadPoolDemo4.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-11-8
 */
public class ThreadPoolDemo4 {
    private static ThreadLocal<String> localMap1 = new ThreadLocal<>();
    private static ThreadLocal<String> localMap2 = new ThreadLocal<>();
    private static ThreadLocal<String> localMap3 = new ThreadLocal<>();

    private static final int THREAD_LOOP_SIZE = 1;
    private static final int MOCK_DIB_DATA_LOOP_SIZE = 1000;
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        /*ThreadLocal<String> localMap = new ThreadLocal<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                localMap1.set("123");
                *//*localMap2.set("321");
                localMap3.set("345");*//*
                System.out.println(Thread.currentThread().getName());
            });
        }*/


        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_LOOP_SIZE);

        for (int i = 0; i < THREAD_LOOP_SIZE; i++) {
            for (int j = 0; j < MOCK_DIB_DATA_LOOP_SIZE; j++) {
                int index = j;
                executorService.execute(() -> threadLocal.set(("123" + index).toString()));
            }
        }
    }
}
