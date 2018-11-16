package com.heitian.ssm.test.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0
 * @类名: LockMoreConditionDemo.java
 * @描述: 使用Lock对象和多个Condition实现等待/通知实例
 * 可以看出实现了分别通知。因此，我们可以使用Condition进行分组，可以单独的通知某一个分组，
 * 另外还可以使用signalAll()方法实现通知某一个分组的所有等待的线程。
 * @创建人: CM
 * @创建时间: 2018-10-31
 */
public class LockMoreConditionDemo {
    private ReentrantLock lock = new ReentrantLock(true);//默认非公平锁
    //private Lock lock = new ReentrantLock(false);//即先进先出，那么他就是公平的；非公平是一种抢占机制，是随机获得锁，
    // 并不是先来的一定能先得到锁，结果就是不公平的。
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        LockMoreConditionDemo demo = new LockMoreConditionDemo();
        new Thread(() -> demo.await(demo.conditionA), "thread0_conditionA").start();
        new Thread(() -> demo.await(demo.conditionA), "thread1_conditionA").start();
        new Thread(() -> demo.await(demo.conditionB), "thread2_conditionB_0").start();
        new Thread(() -> demo.await(demo.conditionB), "thread2_conditionB").start();
        new Thread(() -> demo.await(demo.conditionB), "thread3_conditionB").start();
        new Thread(() -> demo.await(demo.conditionB), "thread3_conditionB_1").start();
        new Thread(() -> demo.signal(demo.conditionA), "thread4_conditionA").start();
        Thread.sleep(1000);
        System.out.println("稍等5秒再通知其他的线程！");
        Thread.sleep(5000);
        System.out.println();
        new Thread(() -> demo.signal(demo.conditionB), "thread5_conditionB").start();
    }

    private void await(Condition condition) {
        try {
            lock.lock();
            System.out.println("开始等待await！ThreadName:" + Thread.currentThread().getName() + "时间：" + System.currentTimeMillis());
            condition.await();
            System.out.println("结束等待await！ThreadName:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void signal(Condition condition) {
        lock.lock();
        System.out.println("发送通知signal!ThreadName:" + Thread.currentThread().getName());
        //condition.signal();
        condition.signalAll();
        lock.unlock();
    }
}
