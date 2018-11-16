package com.heitian.ssm.test.SynchronizedTest;


public class SynchronizedDemo {

    public static void main(String[] args) {
        synchronized (SynchronizedDemo.class) {
        }
        method();
    }

    public static synchronized void method() {
        System.out.println("aa");
    }
}
