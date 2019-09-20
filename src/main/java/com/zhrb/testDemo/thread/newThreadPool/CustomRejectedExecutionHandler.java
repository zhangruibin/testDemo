package com.zhrb.testDemo.thread.newThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName CustomRejectedExecutionHandler
 * @Description TODO
 * @Author zhrb
 * @Date 2019/9/20 16:16
 * @Version
 */
@Slf4j
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("向线程中添加任务被拒绝...");
        //1.当前线程执行
        r.run();

        //2.异常抛出
        throw new RejectedExecutionException("Task " + r.toString() +
        " rejected from" + executor.toString());

    }
}
