package org.ybpeng.thread.demo.future;


import java.util.concurrent.*;

/*
* 利用FutureTask和ExecutorService，可以用多线程的方式提交计算任务，主线程继续执行其他任务，
* 当主线程需要子线程的计算结果时，在异步获取子线程的执行结果。
* */
public class FutureTaskTest {

    public static void main(String[] args) {
        Task task = new Task();// 新建异步任务
        FutureTask<Integer> future = new FutureTask<Integer>(task) {
            // 异步任务执行完成，回调
            @Override
            protected void done() {
                try {
                    System.out.println("future.done():" + get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        // 创建线程池（使用了预定义的配置）
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(future);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        // 可以取消异步任务
//         future.cancel(true);

        try {
            // 阻塞，等待异步任务执行完毕-获取异步任务的返回值
            System.out.println("阻塞，等待异步任务执行完毕-获取异步任务的返回值 future.get():" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    // 异步任务
    static class Task implements Callable<Integer> {
        // 返回异步任务的执行结果
        @Override
        public Integer call() throws Exception {
            int i = 1;
            for (i = 1; i <= 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + "_" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return i;
        }
    }
}
