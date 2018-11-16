/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

package com.heitian.ssm.test;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Deam :
 *
 * @author chenming
 * @version 1.00
 * @since 2018-01-15 15:11
 */
public class Demo {
    public Integer count = 0;

    public static void main(String[] args) {
        final Demo demo = new Demo();
        Executor executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    demo.count++;
                }
            });
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("value" + demo.count);
    }
}
