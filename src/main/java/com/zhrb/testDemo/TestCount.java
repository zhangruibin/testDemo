package com.zhrb.testDemo;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName TestCount
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/23 17:07
 * @Version
 */
public class TestCount {
    public static void main(String[] args) {
        double random = Math.random();
        Random rnd = new Random();
        rnd.nextInt();
        AtomicInteger count = new AtomicInteger();
        LongAdder longAdder = new LongAdder();
    }

    private static void testCount1(){
        int count = 0;
        if (count < 500){
            count++;
        }
    }
}
