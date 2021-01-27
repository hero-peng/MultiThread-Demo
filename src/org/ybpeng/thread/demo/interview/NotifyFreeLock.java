package org.ybpeng.thread.demo.interview;


import java.util.ArrayList;
import java.util.List;

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */
public class NotifyFreeLock {


    private List<String> list = new ArrayList<String>();

    void add(String str){
        list.add(str);
    }

    int getSize(){
        return list.size();
    }

    public static void main(String[] args) {


        NotifyFreeLock obj = new NotifyFreeLock();

        Object lock = new Object();


        //先启动T2
        Thread t2 = new Thread(() ->{
            synchronized (lock){
                if (obj.getSize() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("T2 thread end here...");
            }
        });


        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock) {
                    obj.add(Integer.toString(i));
                    System.out.println("T1 thread,list size = " + obj.getSize());
                    if (obj.getSize() == 5) {
                        //如果size等于5，则通知T2结束
                        System.out.println("T1 thread notify T1 ");
                        lock.notify();
                    }
                }
            }
        });

        t2.start();

        t1.start();

    }
}
