package com.zhrb.testDemo;

/**
 * @ClassName DoubleCheck
 * @Description TODO
 * @Author zhrb
 * @Date 2019/9/23 16:20
 * @Version
 */
public class DoubleCheck {
    private static volatile DoubleCheck doubleCheck = null;
    private  DoubleCheck(){}
    public static synchronized DoubleCheck getDoubleCheck(DoubleCheck doubleCheck){
        if (doubleCheck == null){
            synchronized (DoubleCheck.class){
                if (doubleCheck == null){
                    doubleCheck = new DoubleCheck();
                }
            }
        }
        return doubleCheck;
    }
}
