package test.xianchen;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @类名: Main.java
 * @描述: 我们有 10 个随机时间休眠的线程 (例如，模拟搜索)，然后当其中一个完成，就中断其余的。
 * @创建人: CM
 * @创建时间: 2018-5-31
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("Searcher");
        Result result = new Result();
        SearchTask searchTask = new SearchTask(result);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
        System.out.printf("Information about the Thread Group\n");
        threadGroup.list();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (int i = 0; i < threadGroup.activeCount(); i++) {
            System.out.printf("Thread %s: %s\n", threads[i].getName(), threads[i].getState());
        }
        waitFinish(threadGroup);
        threadGroup.interrupt();
    }

    private static void waitFinish(ThreadGroup threadGroup) throws InterruptedException {
        while (threadGroup.activeCount() > 4) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
