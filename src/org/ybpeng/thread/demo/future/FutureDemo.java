package org.ybpeng.thread.demo.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {

    public static void main(String[] args) {
        ExecutorService newFixedThreadPool = Executors.newSingleThreadExecutor();
        Future<String> submit = newFixedThreadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // TODO Auto-generated method stub
                Thread.sleep(2000);
                return "我是生产的结果";
            }
        });
        System.out.println("=============================== isCancelled = " + submit.isCancelled());
        try {
            System.out.println("我来拿结果了："+submit.get());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        newFixedThreadPool.shutdown();
    }
}
