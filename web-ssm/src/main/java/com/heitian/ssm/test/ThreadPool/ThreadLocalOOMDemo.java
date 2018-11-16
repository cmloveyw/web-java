package com.heitian.ssm.test.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @类名: ThreadLocalOOMDemo.java
 * @描述: （1）key 使用强引用：引用的ThreadLocal的对象被回收了，但是ThreadLocalMap还持有ThreadLocal的强引用，
 * 如果没有手动删除，ThreadLocal不会被回收，导致Entry内存泄漏。
 * <p>
 * （2）key 使用弱引用：引用的ThreadLocal的对象被回收了，由于ThreadLocalMap持有ThreadLocal的弱引用，即使没有手动删除，
 * ThreadLocal也会被回收。value在下一次ThreadLocalMap调用set、get、remove的时候会被清除。
 * <p>
 * 比较两种情况，我们可以发现：由于ThreadLocalMap的生命周期跟Thread一样长，如果都没有手动删除对应key，都会导致内存泄漏，
 * 但是使用弱引用可以多一层保障：弱引用ThreadLocal不会内存泄漏，对应的value在下一次ThreadLocalMap调用set、get、remove的时候会被清除。
 * <p>
 * 因此，ThreadLocal内存泄漏的根源是：由于ThreadLocalMap的生命周期跟Thread一样长，如果没有手动删除对应key就会导致内存泄漏，
 * @创建人: CM
 * @创建时间: 2018-11-13
 */
public class ThreadLocalOOMDemo {

    private static final int THREAD_LOOP_SIZE = 500;
    private static final int MOCK_DIB_DATA_LOOP_SIZE = 10000;

    private static ThreadLocal<List<User>> threadLocal = new ThreadLocal<List<User>>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_LOOP_SIZE);
        for (int i = 0; i < THREAD_LOOP_SIZE; i++) {
            executorService.execute(() -> {
                threadLocal.set(new ThreadLocalOOMDemo().addBigList());
                System.out.println(Thread.currentThread().getName());
                //threadLocal.remove(); //不取消注释的话就可能出现OOM
            });
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //executorService.shutdown();
    }


    private List<User> addBigList() {
        List<User> params = new ArrayList<>(MOCK_DIB_DATA_LOOP_SIZE);
        for (int i = 0; i < MOCK_DIB_DATA_LOOP_SIZE; i++) {
            params.add(new User("cm", "password" + i, i));
        }
        return params;
    }

    class User {
        private String userName;
        private String password;
        private int age;

        public User(String userName, String password, int age) {
            this.userName = userName;
            this.password = password;
            this.age = age;
        }
    }
}
