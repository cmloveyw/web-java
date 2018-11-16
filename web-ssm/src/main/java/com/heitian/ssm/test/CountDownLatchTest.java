package com.heitian.ssm.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @类名: CountDownLatchTest.java
 * @描述: CountDownLatch的构造函数接收一个int类型的参数作为计数器，如果你想等待N个点完成，这里就传入N。
 * 当我们调用一次CountDownLatch的countDown方法时，N就会减1，CountDownLatch的await会阻塞当前线程，直到N变成零。
 * 由于countDown方法可以用在任何地方，所以这里说的N个点，可以是N个线程，也可以是1个线程里的N个执行步骤。
 * 用在多个线程时，你只需要把这个CountDownLatch的引用传递到线程里。
 * @创建人: CM
 * @创建时间: 2018-5-25
 */
public class CountDownLatchTest {
    static CountDownLatch c = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();
            }
        }).start();
        long aa = 5000;
        c.await(aa, TimeUnit.MILLISECONDS);
        System.out.println("3");
    }

}
