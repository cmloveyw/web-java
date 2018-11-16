package com.heitian.ssm.test.reentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @version 1.0
 * @类名: ReentrantReadWriteLockDemo.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-11-1
 */
public class ReentrantReadWriteLockDemo {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();
        //读线程
        /*可以看出两个线程之间，获取锁的时间几乎同时，说明lock.readLock().lock(); 允许多个线程同时执行lock（）方法后面的代码。*/
        /*new Thread(() -> demo.read(), "ThreadA").start();
        new Thread(() -> demo.read(), "ThreadB").start();*/


        /*for (int i = 0; i < 10; i++) {
            ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();
            new Thread(() -> demo.read(), "" + i + "").start();
        }*/


        //写线程
        /*可以看出执行结果大致差了5秒的时间，可以说明多个写线程是互斥的。*/
        /*new Thread(() -> demo.write(), "ThreadA").start();
        new Thread(() -> demo.write(), "ThreadB").start();*/

        //读写互斥或写读互斥
        /*可以看出执行结果大致差了3秒的时间，可以说明读写线程是互斥的。*/
        /*fixedThreadPool.execute(() -> demo.read());
        Thread.sleep(1000);
        fixedThreadPool.execute(() -> demo.write());*/

        new Thread(() -> demo.write(), "Threadc").start();
        new Thread(() -> demo.read(), "ThreadA").start();
        //Thread.sleep(1000);
        new Thread(() -> demo.write(), "ThreadB").start();
    }

    private void read() {
        try {
            lock.readLock().lock();
            System.out.println("获取读锁" + Thread.currentThread().getName() + "时间：" + System.currentTimeMillis());
            //模拟读操作5秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    private void write() {
        try {
            lock.writeLock().lock();
            System.out.println("获取写锁" + Thread.currentThread().getName() + "时间：" + System.currentTimeMillis());
            //模拟写操作5秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
