package org.ybpeng.thread.demo.sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CountdownLatchDemo   {

    //用于聚合所有的统计指标
    private static Map map=new HashMap();

    //创建计数器，这里需要统计4个指标
    private static CountDownLatch countDownLatch=new CountDownLatch(4);
    //分别统计4个指标用户新增数量、订单数量、商品的总销量、总销售额；

    public static void main(String[] args) {

        Thread T1 = new Thread( () -> {
            System.out.println("T1 统计用户新增数量");
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //对计数器进行递减1操作，当计数器递减至0时，当前线程会去唤醒阻塞队列里的所有线程。
            countDownLatch.countDown();
        });

        Thread T2 = new Thread( () -> {
            System.out.println("T2 统计 订单数量");
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //对计数器进行递减1操作，当计数器递减至0时，当前线程会去唤醒阻塞队列里的所有线程。
            countDownLatch.countDown();
        });

        Thread T3 = new Thread( () -> {
            System.out.println("T3 统计 订单数量");
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //对计数器进行递减1操作，当计数器递减至0时，当前线程会去唤醒阻塞队列里的所有线程。
            countDownLatch.countDown();
        });

        Thread T4 = new Thread( () -> {
            System.out.println("T4 统计 订单数量");
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //对计数器进行递减1操作，当计数器递减至0时，当前线程会去唤醒阻塞队列里的所有线程。
            countDownLatch.countDown();
        });
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("begin time = " + df.format(new Date()));

        T1.start();
        T2.start();
        T3.start();
        T4.start();

        try {
            countDownLatch.await();//阻塞当前线程，将当前线程加入阻塞队列。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("begin end = " + df.format(new Date()));

    }
}
