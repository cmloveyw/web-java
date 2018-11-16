package com.heitian.ssm.test.executorServiceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @version 1.0
 * @类名: ExecutorServiceTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class ExecutorServiceTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i < 15; i++) {
            Future<String> future = executorService.submit(new TaskWithResult(i));
            futures.add(future);
        }
        executorService.shutdown();
        for (Future<String> fs : futures) {
            try {
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}

class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        System.out.println("call()方法被自动调用，干活！！" + Thread.currentThread().getName());
        Thread.sleep(10);//模拟干活消耗时间
        return "call()方法被自动调用，任务结果是：" + id + Thread.currentThread().getName();
    }
}
