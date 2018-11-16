package com.heitian.ssm.test;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @类名: VolatileInc.java
 * @描述: volatile 并不能保证线程安全性！
 * 这是因为虽然 volatile 保证了内存可见性，每个线程拿到的值都是最新值，
 * 但 count++ 这个操作并不是原子的，这里面涉及到获取值、自增、赋值的操作并不能同时完成。
 * @创建人: CM
 * @创建时间: 2018-5-24
 */

//所以想到达到线程安全可以使这三个线程串行执行(其实就是单线程，没有发挥多线程的优势)。
//也可以使用 synchronize 或者是锁的方式来保证原子性。
//还可以用 Atomic 包中 AtomicInteger 来替换 int，它利用了 CAS 算法来保证了原子性。
public class VolatileInc implements Runnable {
    private static volatile int count = 0; //使用 volatile 修饰基本数据内存不能保证原子性

    //private static AtomicInteger count = new AtomicInteger();

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                count++;
                //count.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        aa();
    }

    public static void aa() throws InterruptedException {
        VolatileInc volatileInc = new VolatileInc();
        Thread t1 = new Thread(volatileInc, "t1");
        Thread t2 = new Thread(volatileInc, "t2");
        t1.start();
        //t1.join();
        t2.start();
        //t2.join();
//        for (int i = 0; i < 10000; i++) {
//            count++;
//            //count.incrementAndGet();
//        }
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println("最终Count=" + count);
    }
}