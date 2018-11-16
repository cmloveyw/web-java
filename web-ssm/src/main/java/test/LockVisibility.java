package test;

/**
 * @version 1.0
 * @类名: LockVisibility.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-4-25
 */
public class LockVisibility {
    private static boolean ready;
    private static int number;

    private static class ReadThread extends Thread {
        @Override
        public void run() {
            synchronized (NoVisibility.class) { //lock
                while (!ready) {
                    Thread.yield();
                }
                System.out.println(number);
            }
        }
    }

    public static void main(String[] args) {
        new ReadThread().start();
        synchronized (NoVisibility.class) {  //lock
            number = 42;
            ready = true;
        }
    }
}