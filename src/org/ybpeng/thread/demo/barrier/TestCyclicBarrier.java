package org.ybpeng.thread.demo.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {
    public static void main(String[] args) {
        //CyclicBarrier barrier = new CyclicBarrier(20);

        CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("满人"));

//        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("满人，发车");
//            }
//        });

        for(int i=0; i<30; i++) {

            new Thread(()->{
                try {
                    System.out.println("iiii = " + Thread.currentThread().getName());
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
}
