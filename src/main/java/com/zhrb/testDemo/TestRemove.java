package com.zhrb.testDemo;

import java.util.*;

/**
 * @ClassName TestRemove
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/19 11:22
 * @Version
 */
public class TestRemove {
    public static void main(String[] args) {
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        iteratorRemove(a);
        System.out.println(a.toString());
    }
    private static void forEachRemove(List<String> a){
        for (String temp : a) {
            if("1".equals(temp)){
                a.remove(temp);
            }
        }
    }
    private static void iteratorRemove(List<String> a){
        Iterator<String> it = a.iterator();
        while(it.hasNext()){
            String temp = it.next();
            if("1".equals(temp)){
                it.remove();
            }
        }
    }

    private static void foreachMap(){
        Map map = new HashMap();
    }
}
