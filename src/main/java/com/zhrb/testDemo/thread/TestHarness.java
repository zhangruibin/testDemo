package com.zhrb.testDemo.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName TestHarness
 * @Description
 * @Author Administrator
 * @Date 2019/10/28 16:02
 * @Version
 */
public class TestHarness {
    public static void main(String[] args) {
        TestHarness s = new TestHarness();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("New runnable.");
            }
        };
        try {
            long l = s.timeTasks(2, runnable);
            System.out.println("Countdown time is:"+ l +" nanomilles.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startDate = new CountDownLatch(1);
        final CountDownLatch endDate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++){
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        startDate.await();
                        try {
                            task.run();
                        }catch (RuntimeException e){
                            e.printStackTrace();
                        }finally {
                            endDate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        startDate.countDown();
        endDate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
