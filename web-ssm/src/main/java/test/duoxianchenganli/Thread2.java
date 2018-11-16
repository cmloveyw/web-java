package test.duoxianchenganli;

/**
 * @version 1.0
 * @类名: Thread2.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-5
 */
public class Thread2 implements Runnable {
    private Object object;

    public Thread2(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        synchronized (object) {
            for (char c = 'A'; c <= 'Z'; c++) {
                System.out.print(c + " ");
                object.notifyAll();
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
