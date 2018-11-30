package com.heitian.ssm.test.CASTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @类名: AtomicIntegerDemo.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-11-30
 */
public class AtomicIntegerDemo {
    private static AtomicInteger integer = new AtomicInteger(10);
    private static int a = 10;

    public static void main(String[] args) {
        //启动一百个线程同时去抢占cpu，有可能产生并发
        int count = 5000;

        //利用发令枪操作
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread() {
                public void run() {
                    a = a - 1;
                    if (a < 0) {
                        System.out.println("lalal");
                    } else {
                        System.out.println(a);
                    }

                   /* if (integer.get() < 0) {
                        System.out.println("lalal");
                    } else {
                        System.out.println(integer.getAndDecrement());
                    }*/
                }
            }.start();
            //递减锁存器的计数，如果计数到达零，则释放所有等待的线程
            countDownLatch.countDown();
        }
        try {
            //使线程在锁存器倒计数至零之前一直等待
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
