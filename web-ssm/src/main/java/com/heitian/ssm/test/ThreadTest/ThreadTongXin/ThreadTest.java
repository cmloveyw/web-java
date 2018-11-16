package com.heitian.ssm.test.ThreadTest.ThreadTongXin;

/**
 * @version 1.0
 * @类名: ThreadTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        /*MyList myList = new MyList();
        ThreadA a = new ThreadA(myList);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(myList);
        b.setName("B");
        b.start();*/


        /*wait/notify线程间通信示例代码->上述实例已经实现了简单的等待通知机制，并且我们也可以看到，
        虽然线程B在第五个元素的时候发出通知，而线程A实现线程B执行完之后才获得对象锁，这也可以说明，
        wait方法是释放锁的而notify方法是不释放锁的。*/
        Object lock = new Object();
        ThreadB b = new ThreadB(lock);
        b.setName("B");
        b.start();
        Thread.sleep(50);
        ThreadA a = new ThreadA(lock);
        a.setName("A");
        a.start();
    }
}
