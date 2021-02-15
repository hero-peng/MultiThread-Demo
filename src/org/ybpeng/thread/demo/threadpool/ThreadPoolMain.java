package org.ybpeng.thread.demo.threadpool;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* 创建线程池
* */
public class ThreadPoolMain {



    public static void main(String[] args) {

        //创建使用单个线程的线程池
        ExecutorService es1 = Executors.newSingleThreadExecutor();

        //创建一个会根据需要创建新线程的线程池
        ExecutorService es2 = Executors.newCachedThreadPool();

        //创建使用固定线程数的线程池
        ExecutorService es3 = Executors.newFixedThreadPool(5);

        final Random random = new Random();

        final List<Integer> list = new ArrayList();

        long start = System.currentTimeMillis();

//        for (int i = 0;i< 100000;i++){
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    list.add(random.nextInt());
//                }
//            });
//        }

        for (int i = 0;i< 100;i++){

            es2.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread name: " + Thread.currentThread().getName());
                    list.add(random.nextInt());
                }
            });
        }
        es2.shutdown();
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("list size = " + list.size());

    }
}
