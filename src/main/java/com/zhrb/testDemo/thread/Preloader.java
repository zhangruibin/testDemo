package com.zhrb.testDemo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName Preloader
 * @Description
 * @Author zhrb
 * @Date 2019/10/29 9:23
 * @Version
 */
public class Preloader {
    private final FutureTask<ProductInfo> futureTask = new
            FutureTask<ProductInfo>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws Exception {
            Preloader preloader = new Preloader();
            return preloader.get();
        }
    });

    private final Thread thread = new Thread(futureTask);

    public void start(){thread.start();}

    public ProductInfo get() throws ExecutionException, InterruptedException {
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ExecutionException)
                e.printStackTrace();
            else
                e.getMessage();
        }finally {
            return futureTask.get();
        }
    }

    private class ProductInfo{
        public String name;

        public Long id;
    }
}
