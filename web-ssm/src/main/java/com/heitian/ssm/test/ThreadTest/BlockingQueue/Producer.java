package com.heitian.ssm.test.ThreadTest.BlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @类名: Producer.java
 * @描述: 生产线程
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class Producer implements Runnable {
    private volatile boolean isRunning = true;//标志是否在运行
    private BlockingQueue queue;//阻塞队列
    private static AtomicInteger count = new AtomicInteger();//自动更新的值
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String data;
        Random random = new Random();
        System.out.println(Thread.currentThread().getName() + "启动生产线程！");
        while (isRunning) {
            System.out.println(Thread.currentThread().getName() + "正在生产数据。。。。");
            try {
                Thread.sleep(random.nextInt(DEFAULT_RANGE_FOR_SLEEP));//取0~DEFAULT_RANGE_FOR_SLEEP值的一个随机数
                //Thread.sleep(DEFAULT_RANGE_FOR_SLEEP);
                data = "data:" + count.incrementAndGet();//以原子方式将count当前值加1
                System.out.println(Thread.currentThread().getName() + "将数据：" + data + "放入队列。。。");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {////设定的等待时间为2s，如果超过2s还没加进去返回true
                    System.out.println(Thread.currentThread().getName() + "放入数据失败：" + data);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(Thread.currentThread().getName() + "退出生产线程！");
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
