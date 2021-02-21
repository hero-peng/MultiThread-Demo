package org.ybpeng.thread.demo.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 {

    static int var = 0;
    static ReentrantLock mainLock = new ReentrantLock(true);

    public static void count(){

        try {
            mainLock.lock();
            for (int i = 0;i<10000;i++){
                var++;
            }
        }catch (Exception e){
            System.out.println(" e = " + e);
        }finally {
            mainLock.unlock();
        }
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(()->{
            count();
        },"thread1");

        Thread thread2 = new Thread(()->{
            count();
        },"thread2");

        Thread thread3 = new Thread(()->{
            count();
        },"thread3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        }catch (Exception e){

        }

        System.out.println("var = " + var);

    }
}
