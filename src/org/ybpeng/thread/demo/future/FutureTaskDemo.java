package org.ybpeng.thread.demo.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {

    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        System.out.println("主线程do something");
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            public Integer call() throws Exception {

                Thread.sleep(2000);
                System.out.println("子线程执行耗时操作");
                return 100;
            }
        });
        es.execute(futureTask);
        System.out.println("我不管子线程，我需要干点其他事，主线程继续do something");
        try {
            System.out.println("执行结果为" + futureTask.get());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        es.shutdown();
    }
}
