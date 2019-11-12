package com.zhrb.testDemo.thread;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

/**
 * @ClassName MyAppThread
 * @Description
 * @Author zhrb
 * @Date 2019/11/7 8:57
 * @Version
 */
public class MyAppThread extends Thread{
    Logger log = LoggerFactory.getLogger(MyAppThread.class);
    public static final String DEFULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();

    public MyAppThread(Runnable r){
        this(r,DEFULT_NAME);
    }
    public MyAppThread(Runnable runnable,String name){
        super(runnable,name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.info((Marker) Level.SEVERE,"UNCAUGHT inthread " + t.getName(),e);
            }
        });
    }
    @Override
    public void run() {
        //复制debug标志以确保一致的值
        boolean debug = debugLifecycle;
        if (debug)
            log.info((Marker) Level.FINE,"Created "+ getName());
        try {
            alive.incrementAndGet();
            super.run();
        }finally {
            alive.decrementAndGet();
            if (debug)
                log.info((Marker) Level.FINE,"Exiting "+ getName());
        }

    }
    //声明方法，方便外部调用，同时保持方法的一致性以及线程安全
    public static int getThreadsCreated(){return created.get();}
    public static int getThreadAlive(){return alive.get();}
    public static boolean getDebug(){return debugLifecycle;}
    public static void setDebug(boolean b){debugLifecycle = b;}
}
