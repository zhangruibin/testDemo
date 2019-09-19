package com.zhrb.testDemo;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName TestCollection
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/19 11:07
 * @Version
 */
public class TestCollection {

    public static void main(String[] args) {
        //使用工具类Arrays.asList()把数组转换成集合时，
        //不能使用其修改集合相关的方法，
        //它的add/remove/clear方法会抛出UnsupportedOperationException异常。
        //说明：asList的返回对象是一个Arrays内部类，并没有实现集合的修改方法。
        //Arrays.asList体现的是适配器模式，只是转换接口，后台的数据仍是数组。
        String[] str = new String[] { "a", "b" };
        List list = Arrays.asList(str);
        System.out.println(list.get(0));
        System.out.println("------------------------------");
        System.out.println(list.add("10086"));
    }
}
