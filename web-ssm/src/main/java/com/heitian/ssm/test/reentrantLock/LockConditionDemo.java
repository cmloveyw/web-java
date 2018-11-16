package com.heitian.ssm.test.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0
 * @类名: LockConditionDemo.java
 * @描述: 使用Lock对象和Condition实现等待/通知实例
 * （1）Object的wait()方法相当于Condition类中的await()方法；
 * （2）Object的notify()方法相当于Condition类中的signal()方法；
 * （3）Object的notifyAll()方法相当于Condition类中的signalAll()方法；
 * @创建人: CM
 * @创建时间: 2018-10-31
 */
public class LockConditionDemo {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        //使用同一个LockConditionDemo对象，使得lock、condition一样
        LockConditionDemo demo = new LockConditionDemo();
        new Thread(() -> demo.await(), "thread1").start();
        Thread.sleep(1000);
        new Thread(() -> demo.signal(), "thread2").start();
    }

    private void await() {
        try {
            lock.lock();
            System.out.println("开始等待await！ThreadName:" + Thread.currentThread().getName());
            condition.await();
            System.out.println("等待await结束！ThreadName:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void signal() {
        lock.lock();
        System.out.println("发送通知signal!ThreadName:" + Thread.currentThread().getName());
        condition.signal();
        lock.unlock();
    }
}
