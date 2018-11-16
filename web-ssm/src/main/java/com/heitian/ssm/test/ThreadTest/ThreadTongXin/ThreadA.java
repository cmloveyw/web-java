package com.heitian.ssm.test.ThreadTest.ThreadTongXin;

/**
 * @version 1.0
 * @类名: ThreadA.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class ThreadA extends Thread {
    //private MyList list;
    private Object lock;

    public ThreadA(Object lock) {
        super();
        //this.list = list;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            /*for (int i = 0; i < 10; i++) {
                list.add();
                System.out.println(Thread.currentThread().getName() + "添加了" + (i + 1) + "个元素");
                Thread.sleep(500);
            }*/

            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    MyList.add();
                    if (MyList.size() == 5) {
                        lock.notify();
                        System.out.println("已发出通知！");
                    }
                    System.out.println("添加了" + (i + 1) + "个元素");
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
