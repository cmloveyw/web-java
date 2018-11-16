package com.heitian.ssm.test.ThreadTest.BlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @类名: Consumer.java
 * @描述: 消费者线程
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class Consumer implements Runnable {
    private BlockingQueue<String> queues;
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

    public Consumer(BlockingQueue<String> queues) {
        this.queues = queues;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "启动消费者线程！");
        Random random = new Random();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println(Thread.currentThread().getName() + "正从队列获取数据。。。");
            try {
                String data = queues.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println(Thread.currentThread().getName() + "拿数据：" + data);
                    Thread.sleep(random.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                    //Thread.sleep(DEFAULT_RANGE_FOR_SLEEP);
                } else {
                    isRunning = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(Thread.currentThread().getName() + "退出消费者线程");
            }
        }
    }
}
