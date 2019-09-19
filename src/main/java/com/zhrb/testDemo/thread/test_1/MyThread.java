package com.zhrb.testDemo.thread.test_1;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/19 14:37
 * @Version
 */
public class MyThread extends Thread{

    public static void main(String[] args) {
        MyThread a = new MyThread("A");
        MyThread b = new MyThread("B");
        MyThread c = new MyThread("C");
        a.start();
        b.start();
        c.start();
    }
    private int count = 5;

    public MyThread(String name) {
        super();
        this.setName(name);
    }
    @Override
    public void run() {
        super.run();
        while (count > 0) {
            count--;
            System.out.println("由 " + MyThread.currentThread().getName() + " 计算，count=" + count);
        }
    }

}
