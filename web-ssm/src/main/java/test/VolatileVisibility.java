package test;

/**
 * @version 1.0
 * @类名: VolatileVisibility.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-4-25
 */
public class VolatileVisibility {
    private static volatile boolean ready; //use volatile variable
    private static int number;

    private static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReadThread().start();
        number = 42;
        ready = true;
    }
}
