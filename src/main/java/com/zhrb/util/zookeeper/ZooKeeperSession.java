package com.zhrb.util.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ZooKeeperSession
 * @Description
 * @Author Administrator
 * @Date 2019/11/11 16:30
 * @Version
 */
public class ZooKeeperSession {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private ZooKeeper zookeeper;
    private CountDownLatch latch;
    private static Long  waitTime = 30000L;

    public ZooKeeperSession(){
        try {
            this.zookeeper = new ZooKeeper("192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181",50000,new ZooKeeperWatcher());
            try {
                connectedSemaphore.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ZooKeeper session established......");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取分布式锁
     * zookeeper的分布式锁原理就是，在zookeeper上创建一个带有象征意义的节点。也就是锁。
     * 因为是在zookeeper上创建的节点，所以可以被zookeeper的公有项目锁监听到，因此当该节点，
     * 也就是锁的状态进行改变的时候，其他的功能型子节点，也就是竟态工程会同步获取该锁的状态是持有还是已经被释放。
     */
    public Boolean accquireDistributedLock(Long productId){
        /**
         * 创建节点
         */
        String path = "/product-lock" + productId;
        try {
            zookeeper.create(path,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            return true;
        } catch (Exception e) {
            while (true){
                try {
                    //相当于给节点设置一个监听器，也就是无限循环监听节点的状态，从而对锁状态进行判断
                    Stat stat = zookeeper.exists(path,true);
                    //也就是节点存在。并未删除(释放锁)
                    if (stat != null){
                        this.latch = new CountDownLatch(1);
                        this.latch.await(waitTime, TimeUnit.MICROSECONDS);
                        this.latch = null;
                    }
                    zookeeper.create(path,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    return true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                    continue;
                }
            }
        }
    }
    /**
     * 释放掉一个分布式锁
     *
     * @param productId
     */
    public void releaseDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;
        try {
            zookeeper.delete(path, -1);
            System.out.println("release the lock for product[id=" + productId + "]......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 建立 zk session 的 watcher
     */
    private class ZooKeeperWatcher implements Watcher {

        private CountDownLatch latch = ZooKeeperSession.getInstance().latch;

        @Override
        public void process(WatchedEvent event) {
            System.out.println("Receive watched event: " + event.getState());

            if (Event.KeeperState.SyncConnected == event.getState()) {
                connectedSemaphore.countDown();
            }

            if (this.latch != null) {
                this.latch.countDown();
            }
        }

    }

    /**
     * 封装单例的静态内部类
     */
    private static class Singleton {
        private static ZooKeeperSession instance;

        static {
            instance = new ZooKeeperSession();
        }

        public static ZooKeeperSession getInstance() {
            return instance;
        }

    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ZooKeeperSession getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化单例的便捷方法
     */
    public static void init() {
        getInstance();
    }

}
