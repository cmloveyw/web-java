package test.Thread;

/**
 * @version 1.0
 * @类名: Daemon.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-26
 */
class Daemon extends Thread {
    private static final int SIZE = 10;
    private Thread[] t = new Thread[SIZE];

    public Daemon() {
        setDaemon(true);
        start();
    }

    public void run() {
        for (int i = 0; i < SIZE; i++)
            t[i] = new DaemonSpawn(i);
        for (int i = 0; i < SIZE; i++)
            System.out.println(
                    "t[" + i + "].isDaemon() = "
                            + t[i].isDaemon());
        while (true)
            yield();
    }
}

class DaemonSpawn extends Thread {
    public DaemonSpawn(int i) {
        System.out.println(
                "DaemonSpawn " + i + " started");
        start();
    }

    public void run() {
        while (true)
            yield();
    }
}


