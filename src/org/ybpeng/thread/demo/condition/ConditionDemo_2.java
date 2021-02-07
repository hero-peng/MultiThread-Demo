package org.ybpeng.thread.demo.condition;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
        两个线程A和B，
        线程A打印1、2、3、4、5、6、7
        线程B打印A、B、C、D、E、F、G
        两个线程交叉打印
        打印结果为：1A2B3C4D5E6F7G
        ReentrantLock
* */
public class ConditionDemo_2 {

    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        String strA  = "1234567";

        String strB  = "ABCDEFG";

        Condition condition = lock.newCondition();

        new Thread(() -> {

            try {
                lock.lock();
                for (int i = 0;i<strA.length();i++){
                    System.out.print(strA.charAt(i));
                    condition.await();
                    condition.signal();
                }
            }catch (Exception e ){
                System.out.println(e);
            }finally {
                lock.unlock();
            }
        },"threadA").start();


        new Thread(() -> {
            try {
                lock.lock();
                for (int j = 0;j<strB.length();j++){
                    int end = j;
                    System.out.print(strB.substring(j,++end));
                    condition.signal();
                    condition.await();
                }
            }catch (Exception e ){
                System.out.println(e);
            }finally {
                lock.unlock();
            }
        },"threadB").start();

    }
}
