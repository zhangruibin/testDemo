package com.zhrb.testDemo.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ToArray
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/20 14:47
 * @Version
 */
public class ToArray {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(2);
        list.add("guan");
        list.add("bao");
        String[] array = new String[list.size()];
        array = list.toArray(array);
        System.out.println();
    }
}
