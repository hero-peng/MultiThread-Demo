package org.ybpeng.thread.demo.condition;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* 我们要打印1到9这9个数字，
* 由A线程先打印1，2，3，
* 然后由B线程打印4,5,6，
* 然后再由A线程打印7，8，9.
* 这道题有很多种解法，现在我们使用Condition来做这道题
* */
public class ConditionDemo_1 {
    //初始化可重入锁
    static Lock lock = new ReentrantLock();

    static public int value = 1;

    public static void main(String[] args) {

        //第一个条件当屏幕上输出到3
        final Condition threeCondition = lock.newCondition();
        //第二个条件当屏幕上输出到6
        final Condition sixCondition = lock.newCondition();

        //NumberWrapper只是为了封装一个数字，一边可以将数字对象共享，并可以设置为final
        //注意这里不要用Integer, Integer 是不可变对象
        //初始化A线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //需要先获得锁
                lock.lock();
                try {
                    System.out.println("threadA start write");
                    //A线程先输出前3个数
                    while (value <= 3) {
                        System.out.println(value);
                        value++;
                    }
                    //输出到3时要signal，告诉B线程可以开始了
                    threeCondition.signal();
                } finally {
                    lock.unlock();
                }
                lock.lock();
                try {
                    //等待输出6的条件
                    sixCondition.await();
                    System.out.println("threadA start write");
                    //输出剩余数字
                    while (value <= 9) {
                        System.out.println(value);
                        value++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        },"threadA"
        ).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    while (value <= 3) {
                        //等待3输出完毕的信号
                        threeCondition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                try {
                    lock.lock();
                    //已经收到信号，开始输出4，5，6
                    System.out.println("threadB start write");
                    while (value <= 6) {
                        System.out.println(value);
                        value++;
                    }
                    //4，5，6输出完毕，告诉A线程6输出完了
                    sixCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        },"threadB"
        ).start();
    }
}
