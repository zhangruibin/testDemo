package com.zhrb.testDemo;

import com.zhrb.entity.Student;

/**
 * @ClassName DoubleCheck
 * @Description TODO
 * @Author zhrb
 * @Date 2019/9/23 16:20
 * @Version
 */
public class DoubleCheck {
    //1、私有静态变量
    private static volatile DoubleCheck doubleCheck = null;
    //业务对象
    private Student student;
    //2、私有构造方法
    private  DoubleCheck(){
        student = new Student();
    }
    //3、公有静态方法，获取实例对象
    //加上同步锁 synchronized，线程安全
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
    //4、公有业务方法
    public String getStudentName(){
        return student.getName();
    }
}
