package com.heitian.ssm.test;

import java.util.concurrent.CyclicBarrier;

/**
 * @version 1.0
 * @类名: CyclicBarrierTest.java
 * @描述: CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。它要做的事情是，
 * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，
 * 所有被屏障拦截的线程才会继续干活。CyclicBarrier默认的构造方法是CyclicBarrier(int parties)，
 * 其参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
 * @创建人: CM
 * @创建时间: 2018-5-25
 */
public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(2, () -> {
        System.out.println("哈哈哈我被优先执行咯！！");
    });

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
                System.out.println(2);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
                System.out.println(4);
            }
        }).start();
        try {
            c.await();
        } catch (Exception e) {
        }
        System.out.println(3);
        /*if (c.getNumberWaiting() == 0) {
            System.out.println("无等待");
            c.reset();
        }*/
    }
}

