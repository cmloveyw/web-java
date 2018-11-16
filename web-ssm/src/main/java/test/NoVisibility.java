package test;

/**
 * @version 1.0
 * @类名: NoVisibility.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-4-25
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
