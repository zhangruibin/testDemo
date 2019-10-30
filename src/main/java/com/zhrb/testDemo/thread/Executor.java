package com.zhrb.testDemo.thread;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName Executor
 * @Description
 * @Author Administrator
 * @Date 2019/10/30 11:18
 * @Version
 */
public class Executor {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10));
        AtomicInteger atomicInteger = new AtomicInteger();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                atomicInteger.getAndIncrement();
                System.out.println("Test thread pool.");
                if (atomicInteger.get() == 3){
                    threadPoolExecutor.shutdown();
                    System.out.println(threadPoolExecutor.isShutdown());
                }else if(atomicInteger.get() == 6){
                    threadPoolExecutor.shutdownNow();
                    System.out.println(threadPoolExecutor.isShutdown());
                }
            }
        });
    }
}
