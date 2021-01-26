package org.ybpeng.thread.demo.interview;


import java.util.ArrayList;
import java.util.List;

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */


public class NotifyFreeLock {


    private List list = new ArrayList<String>();

    int size(){
        return list.size();
    }

    void add(String str){
        list.add(str);
    }


    public static void main(String[] args) {

        NotifyFreeLock lock = new NotifyFreeLock();

        Thread t2 = new Thread(()-> {
            if (lock.size() == 5){
                //线程2结束
                System.out.println("T2 end here");
            }
        });


        /*
        * 线程1往list列表add元素
        * */
        Thread t1 = new Thread(()-> {
            for (int i = 1;i<=10;i++){
                lock.add(Integer.toString(i));
            }
        });


    }
}
