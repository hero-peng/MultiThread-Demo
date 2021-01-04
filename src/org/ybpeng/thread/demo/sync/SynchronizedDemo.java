package org.ybpeng.thread.demo.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedDemo {

    public void func1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println("i = " + i + " ");
            }
            System.out.println("*********************************************");
        }
    }

    public static void main(String[] args) {

        SynchronizedDemo demo = new SynchronizedDemo();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            demo.func1();
        });
        executorService.execute(()->{
            demo.func1();
        });

    }
}
