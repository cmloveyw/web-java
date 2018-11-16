package com.heitian.ssm.test.CountDownLatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @version 1.0
 * @类名: SummonDragonDemo2.java
 * @描述: CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）
 * @创建人: CM
 * @创建时间: 2018-11-5
 */
public class SummonDragonDemo2 {
    /*代码中设置了两个屏障点，第一个用于召集7个法师，等7个法师召集完后，在设置在一个屏障点，
    7位法师去寻找龙珠，然后召唤神龙，中间有个嵌套的关系！*/
    private static final int Thread_count_num = 7;

    public static void main(String[] args) {
        //设置第一个屏障点，等待召集7位法师
        CyclicBarrier cyclicBarrier = new CyclicBarrier(Thread_count_num, new Runnable() {
            @Override
            public void run() {
                System.out.println("7个法师召集完毕，同时出发，去往不同地方寻找龙珠！");
                summonDragon();
            }
        });
        //召集7位法师
        for (int i = 1; i <= Thread_count_num; i++) {
            int index = i;
            new Thread(() -> {
                System.out.println("召集第" + index + "个法师");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 召唤神龙：1、收集龙珠 2、召唤神龙
     * 时间:   2018-11-5 10:43
     * 作者:   CM
     */
    private static void summonDragon() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(Thread_count_num, new Runnable() {
            @Override
            public void run() {
                System.out.println("集齐七颗龙珠！召唤神龙！");
            }
        });
        for (int i = 1; i <= Thread_count_num; i++) {
            int index = i;
            new Thread(() -> {
                try {
                    System.out.println("第" + index + "颗龙珠已收集到！");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
