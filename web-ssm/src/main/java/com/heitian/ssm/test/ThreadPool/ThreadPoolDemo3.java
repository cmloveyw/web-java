package com.heitian.ssm.test.ThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @version 1.0
 * @类名: ThreadPoolDemo3.java
 * @描述: 使用submit的坑
 * <p>
 * execute和submit的区别
 * <p>
 * （1）execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功。
 * 通过以下代码可知execute()方法输入的任务是一个Runnable类的实例。
 * <p>
 * （2）submit()方法用于提交需要返回值的任务。线程池会返回一个future类型的对象，通过这个future对象可以判断任务是否执行成功，
 * 并且可以通过future的get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，而使用get（long timeout，TimeUnit unit）
 * 方法则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。
 * ---------------------
 * @创建人: CM
 * @创建时间: 2018-11-6
 */
public class ThreadPoolDemo3 {
    public static void main(String[] args) {
        /*
         *
         * 时间:   上述代码，可以看出运行结果为4个，因该是有5个的，但是当i=0的时候，100/0是会报错的，
         * 但是日志信息中没有任何信息，是为什么那？如果使用了submit(Runnable task) 就会出现这种情况，
         * 任何的错误信息都出现不了！这是因为使用submit(Runnable task) 的时候，
         * 错误的堆栈信息跑出来的时候会被内部捕获到，所以打印不出来具体的信息让我们查看
         */
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 5; i++) {
            int index = i;
            //executorService.submit(() -> divTask(100, index)); 1
            //executorService.execute(() -> divTask(100, index)); 2

            Future future = executorService.submit(() -> divTask(100, index));
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    private static void divTask(int a, int b) {
        double result = a / b;
        System.out.println(result);
    }
}
