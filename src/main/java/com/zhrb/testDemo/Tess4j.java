package com.zhrb.testDemo;

/**
 * @ClassName Tess4j
 * @Description
 * @Author Administrator
 * @Date 2019/10/31 15:38
 * @Version
 */
public class Tess4j {
    public static void main(String[] args) {
        System.out.println("关闭之前");
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("当前电脑的cpu核数为：" + i);
        System.exit(0);
        System.out.println("关闭之后");
    }
}
