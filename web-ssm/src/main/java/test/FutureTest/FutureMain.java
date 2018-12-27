package test.FutureTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @version 1.0
 * @类名: FutureMain.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-12-12
 */
public class FutureMain {
    private static String result = "";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new RealData("hello"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(futureTask);
        System.out.println("请求完毕---" + System.currentTimeMillis());
        Thread.sleep(2000);
        System.out.println("这里经过了一个2秒的操作！---" + System.currentTimeMillis());
        System.out.println("真是数据：" + futureTask.get() + System.currentTimeMillis());
        executorService.shutdown();
    }
}
