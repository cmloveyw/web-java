package com.heitian.ssm.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0
 * @类名: ReentrantLockDemo.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-5-25
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {

        final Count ct = new Count();

        for (int i = 0; i < 2; i++) {

            new Thread() {

                @Override

                public void run() {

                    ct.get();

                }

            }.start();

        }


        for (int i = 0; i < 2; i++) {

            new Thread() {

                @Override

                public void run() {

                    ct.put();

                }

            }.start();

        }

    }

}

class Count {

    final ReentrantLock lock = new ReentrantLock();

    public void get() {

        try {

            lock.lock(); // 加锁

            System.out.println(Thread.currentThread().getName() + "get begin");

            Thread.sleep(1000L);// 模仿干活

            System.out.println(Thread.currentThread().getName() + "get end");

            lock.unlock(); // 解锁

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

    public void put() {

        try {

            lock.lock(); // 加锁

            System.out.println(Thread.currentThread().getName() + "put begin");

            Thread.sleep(1000L);// 模仿干活

            System.out.println(Thread.currentThread().getName() + "put end");

            lock.unlock(); // 解锁

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

}
