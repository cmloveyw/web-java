package test.duoxianchenganli;

/**
 * @version 1.0
 * @类名: ThreadTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-5
 */
public class ThreadTest {
    public static void main(String[] args) {
        Object object = new Object();
        Thread2 thread2 = new Thread2(object);
        Thread1 thread1 = new Thread1(object);

        Thread t2 = new Thread(thread2);
        Thread t1 = new Thread(thread1);

        t2.start();
        t1.start();

    }
}
