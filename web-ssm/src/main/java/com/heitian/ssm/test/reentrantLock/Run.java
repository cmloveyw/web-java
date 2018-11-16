package com.heitian.ssm.test.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0
 * @类名: Run.java
 * @描述: 可以看出，当前线程打印完毕之后释放锁，其他线程才可以获取锁然后进行打印。线程打印的数据是分组打印的，
 * 这是因为当前线程已经持有锁，在当前线程打印完之后才会释放锁，但线程之间打印的顺序是随机的。
 * @创建人: CM
 * @创建时间: 2018-10-31
 */
public class Run {
    /*public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new Thread(() -> runMethod(lock), "thread1").start();
        new Thread(() -> runMethod(lock), "thread2").start();
        new Thread(() -> runMethod(lock), "thread3").start();
        new Thread(() -> runMethod(lock), "thread4").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runMethod(lock);
            }
        }, "thread5").start();
    }

    private static void runMethod(Lock lock) {
        lock.lock();
        for (int i = 1; i <= 5; i++) {
            System.out.println("ThreadName:" + Thread.currentThread().getName() + ("i=" + i));
        }
        System.out.println();
        lock.unlock();
    }*/


    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new Thread(() -> runMethod(lock, 0), "thread1").start();
        new Thread(() -> runMethod(lock, 5000), "thread2").start();
        new Thread(() -> runMethod(lock, 1000), "thread3").start();
        new Thread(() -> runMethod(lock, 5000), "thread4").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runMethod(lock, 1000);
            }
        }, "thread5").start();
    }

    private static void runMethod(Lock lock, long sleepTime) {
        lock.lock();
        try {
            Thread.sleep(sleepTime);
            System.out.println("ThreadName:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
