package test.duoxianchenganli;

/**
 * @version 1.0
 * @类名: Thread1.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-5
 */
public class Thread1 implements Runnable {
    private Object object;

    public Thread1(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        synchronized (object) {
            for (int i = 1; i <= 26; i++) {
                System.out.print(2 * i - 1 + " ");
                System.out.print(2 * i + " ");
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
