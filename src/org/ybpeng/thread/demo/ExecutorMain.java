package org.ybpeng.thread.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        executorService.shutdownNow();
        System.out.println("Main run");

        executorService.execute(() -> {
        try {
            Thread.sleep(2000);
            System.out.println("Thread run ---------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        });
    }


}
