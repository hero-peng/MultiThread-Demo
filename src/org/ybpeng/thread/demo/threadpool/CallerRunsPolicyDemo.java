package org.ybpeng.thread.demo.threadpool;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
* 线程池的拒绝策略-如果任务被拒绝了，则由调用线程（提交任务的线程）直接执行此任务
* */
public class CallerRunsPolicyDemo {

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);
        RejectedExecutionHandler handler = new MyRejectPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.SECONDS, workQueue, new MyThreadFactory(),handler);
//        executor.prestartAllCoreThreads();
        for (int i = 1; i <= 20; i++) {
            executor.submit(new MyTask(String.valueOf(i)));
        }
        executor.shutdown();
    }

    /*
    * 创建线程的工厂
    * */
    public static class MyThreadFactory implements ThreadFactory{

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "crpt-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    public static class MyTask implements Runnable{

        private String taskName;

        public MyTask(String name) {
            this.taskName = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.toString() + " is running! " + Thread.currentThread().getName() + ":执行任务");
                Thread.sleep(1000); //让任务执行慢点
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "MyTask [name = " + taskName + "]";
        }
    }

    /*
    * 如果任务被拒绝了，则由调用线程（提交任务的线程）直接执行此任务
    * */
    public static class MyRejectPolicy extends ThreadPoolExecutor.CallerRunsPolicy{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            super.rejectedExecution(r, e);
            pringLog(r, e);
        }

        private void pringLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println( r.toString() + " rejected" + ",size = " +  e.getQueue().size());
        }
    }
}
