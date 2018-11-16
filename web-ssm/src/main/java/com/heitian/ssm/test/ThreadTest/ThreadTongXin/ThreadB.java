package com.heitian.ssm.test.ThreadTest.ThreadTongXin;

/**
 * @version 1.0
 * @类名: ThreadB.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class ThreadB extends Thread {
    /*private MyList list;

    public ThreadB(MyList list) {
        super();
        this.list = list;
    }*/
    private Object lock;

    public ThreadB(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            /*while (true) {
                System.out.println("b" + "-" + list.size());
                if (list.size() == 5) {
                    //System.out.println("b" + "-" + list.size());
                    System.out.println("==5l了，线程b要退出了");
                    throw new InterruptedException();
                }
            }*/
            synchronized (lock) {
                if (MyList.size() != 5) {
                    System.out.println("wait begin" + System.currentTimeMillis());
                    lock.wait();
                    System.out.println("wait end" + System.currentTimeMillis());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
