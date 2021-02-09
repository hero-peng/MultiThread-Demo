package org.ybpeng.thread.demo.interview;

/**
 *
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountdownLatchLock {

    private List<String> list = new ArrayList<String>();

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    void add(String str){
        list.add(str);
    }

    int getSize(){
        return list.size();
    }


    public static void main(String[] args) {

        CountdownLatchLock demo = new CountdownLatchLock();


        CountDownLatch latch = new CountDownLatch(1);

        Thread T2 = new Thread( () ->{
            System.out.println("T2 thread begin  = " + df.format(new Date()));
            while (demo.getSize() != 5){
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("T2 thread demo end ===================================================, size  = " + demo.getSize());

        });

        Thread T1 = new Thread( () ->{
            System.out.println("T1 thread begin  = " + df.format(new Date()));

            for (int i = 1;i<=10;i++){
                demo.add(Integer.toString(i));
                System.out.println("T1 thread demo size  = " + demo.getSize());
                if (demo.getSize() == 5){
                    System.out.println("T1 thread countDown here.");
                    latch.countDown();
                    System.out.println("T1 thread countDown getCount : " + latch.getCount());

                    //调度T2线程优先执行，执行完毕后再运行T1
                    try {
                        T2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        T2.start();

        T1.start();

        System.out.println("Main thread here..");

    }





}
