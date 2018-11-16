package com.heitian.ssm.test.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @类名: SummonDragonDemo.java
 * @描述: 构造器中的计数值（count）实际上就是闭锁需要等待的线程数量。这个值只能被设置一次，
 * 而且CountDownLatch没有提供任何机制去重新设置这个计数值。
 * <p>
 * 与CountDownLatch的第一次交互是主线程等待其他线程。主线程必须在启动其他线程后立即调用CountDownLatch.await()方法。
 * 这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。
 * <p>
 * 其他N 个线程必须引用闭锁对象，因为他们需要通知CountDownLatch对象，他们已经完成了各自的任务。这种通知机制是通过
 * CountDownLatch.countDown()方法来完成的；每调用一次这个方法，在构造函数中初始化的count值就减1。所以当N个线程都调用了这个方法，
 * count的值等于0，然后主线程就能通过await()方法，恢复执行自己的任务。
 * @创建人: CM
 * @创建时间: 2018-11-1
 */
public class SummonDragonDemo {
    private static final int THREAD_COUNT_NUM = 7;
    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT_NUM);
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= THREAD_COUNT_NUM; i++) {
            int index = i;
            executorService.execute(() -> {
                System.out.println("第" + index + "颗龙珠已找到");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("集齐");
    }
}
