package com.heitian.ssm.test.ThreadTest.likeBlockingQueue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @类名: MyQueue.java
 * @描述: 使用wait/notify模拟BlockingQueue阻塞队列
 * （1）wait()和notify()方法要在同步块或同步方法中调用，即在调用前，线程也必须获得该对象的对象级别锁。
 * （2）wait方法是释放锁，notify方法是不释放锁的；
 * （3）notify每次唤醒wait等待状态的线程都是随机的，且每次只唤醒一个；
 * （4）notifAll每次唤醒wait等待状态的线程使之重新竞争获取对象锁，优先级最高的那个线程会最先执行；
 * （5）当线程处于wait()状态时，调用线程对象的interrupt()方法会出现InterruptedException异常；
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class MyQueue {
    //1:需要一个承受元素的集合
    private final LinkedList<Object> list = new LinkedList<>();
    //2:需要一个计数器
    private final AtomicInteger count = new AtomicInteger(0);
    //3、需要指定上限和下限
    private final int maxSize = 5;
    private final int minSize = 0;
    //5、初始化锁对象
    private final Object lock = new Object();

    /**
     * put方法
     * 时间:   2018-10-30 14:39
     * 作者:   CM
     *
     * @param obj 元素
     */
    public void put(Object obj) {
        synchronized (lock) {
            while (count.get() == maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(obj);
            count.getAndIncrement();
            System.out.println("元素" + obj + "被添加");
            lock.notify();//通知另外一个阻塞的线程方法
        }
    }

    /**
     * get方法
     * 时间:   2018-10-30 14:40
     * 作者:   CM
     */
    public Object get() {
        Object temp;
        synchronized (lock) {
            while (count.get() == minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count.getAndDecrement();
            temp = list.removeFirst();
            System.out.println("元素" + temp + "被消费");
            lock.notify();
        }
        return temp;
    }

    /**
     * 获取计数器长度
     * 时间:   2018-10-30 14:45
     * 作者:   CM
     */
    private int size() {
        return count.get();
    }

    public static void main(String[] args) {
        final MyQueue myQueue = new MyQueue();
        myQueue.put("a");
        myQueue.put("b");
        myQueue.put("c");
        myQueue.put("d");
        myQueue.put("e");
        System.out.println("当前元素个数：" + myQueue.size());
        Thread t1 = new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                myQueue.put("h" + i);
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            while (true) {
                //myQueue.get();
                try {
                    myQueue.get();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t2");
        t1.start();
        t2.start();
    }
}
