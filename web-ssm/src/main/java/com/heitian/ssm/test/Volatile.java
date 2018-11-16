package com.heitian.ssm.test;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @类名: Volatile.java
 * @描述: 当我们需要在两个线程间依据主内存通信时，通信的那个变量就必须的用 volatile 来修饰：
 * 主线程在修改了标志位使得线程 A 立即停止，如果没有用 volatile 修饰，就有可能出现延迟。
 * @创建人: CM
 * @创建时间: 2018-5-24
 */
public class Volatile implements Runnable {
    private static volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            System.out.println(Thread.currentThread().getName() + "正在运行。。。");
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕");
    }

    public static void main(String[] args) throws InterruptedException {
        Volatile aVolatile = new Volatile();
        new Thread(aVolatile, "thread A").start();
        System.out.println("main 线程正在运行");
        TimeUnit.MILLISECONDS.sleep(1);
        aVolatile.stopThread();
    }

    private void stopThread() {
        flag = false;
    }
}
