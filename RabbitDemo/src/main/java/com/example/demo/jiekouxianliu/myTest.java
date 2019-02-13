package com.example.demo.jiekouxianliu;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @类名: myTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-25
 */
public class myTest {
    private int flag = 10;
    private int threadNum = 10;
    private int timeRound = 1000 * 6;
    private AtomicInteger num = new AtomicInteger(0);

    private void call() {
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                num.set(0);
            }
        }, 0, timeRound);

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "进来了！");
                    num.incrementAndGet();
                    if (num.get() <= flag) {
                        System.out.println(Thread.currentThread().getName() + "执行任务成功！");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "执行任务失败调用超限！");
                    }
                }
            });
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new myTest().call();
    }
}
