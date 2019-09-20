package com.zhrb.testDemo.thread.newThreadPool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName CustomThreadFactory
 * @Description TODO
 * @Author zhrb
 * @Date 2019/9/20 16:01
 * @Version
 */
public class CustomThreadFactory implements ThreadFactory{
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public CustomThreadFactory() {
        SecurityManager securityManager = System.getSecurityManager();
        group = (securityManager != null) ? securityManager.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "custom-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    }

    //自定义创建线程方式
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group,r,
                namePrefix + threadNumber.getAndIncrement(),0 );
        //判断是否是守护进程
        if (t.isDaemon()){
            t.setDaemon(false);
        }
        //判断线程优先级
        if (t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
