package com.heitian.ssm.test.ThreadPool;

import java.util.concurrent.*;

/**
 * @version 1.0
 * @类名: ThreadPoolDemo.java
 * @描述: 1）newFixedThreadPool和newSingleThreadExecutor:
 *   主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
 * 2）newCachedThreadPool和newScheduledThreadPool:
 *   主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
 * @创建人: CM
 * @创建时间: 2018-11-5
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //ExecutorService executors = Executors.newFixedThreadPool(4); 1

        /*ExecutorService executors = new ThreadPoolExecutor(2, 2, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());*/ //2

        /*自定义ThreadFactory、自定义线程拒绝策略
        1、corePoolSize 核心线程池大小；
        2、maximumPoolSize 线程池最大容量大小；
        3、keepAliveTime 线程池空闲时，线程存活的时间；
        4、TimeUnit 时间单位；
        5、ThreadFactory 线程工厂；
        6、BlockingQueue任务队列；
        7、RejectedExecutionHandler 线程拒绝策略；
        列3*/
        ExecutorService executors = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10),
                new ThreadFactory() {//自定义ThreadFactory
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName(r.getClass().getName());
                        return thread;
                    }
                },
                new ThreadPoolExecutor.AbortPolicy()); //自定义线程拒绝策略
        for (int i = 0; i < 10; i++) {
            int index = i;
            executors.submit(() ->
                    System.out.println("i:" + index + "executorService"));
        }
        executors.shutdown();
    }
}
