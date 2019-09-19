package com.zhrb.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName DesensitizeUtil
 * @Description TODO 数据脱敏工具类,基于org.apache.commons.lang3.StringUtils；
 * @Author zhrb
 * @Date 2019/4/10 9:12
 * @Version 1.0.0
 */
public class DesensitizeUtil {
    /**
    *
    * @Description [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例：张**>
    * @Param  [fullName]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:13 2019/4/10
    **/
    public static String chineseName(String fullName) {
        String result = "";
        if (StringUtils.isNotBlank(fullName)) {
            String name = StringUtils.left(fullName, 1);
            result = StringUtils.rightPad(name, StringUtils.length(fullName), "*");
        }
        return result;
    }
    /**
    *
    * @Description 对名字长度进行判断：两个字的脱敏后面[张*];三个字的脱敏中间[张*培];四个字及以上的脱敏前两个以后的[张培**];
    * @Param  [fullName]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:56 2019/4/10
    **/
    public static String fullChineseName(String fullName) {
        String result = "";
        if (StringUtils.isNotBlank(fullName)) {
            //对名字长度进行判断：两个字的脱敏后面;三个字的脱敏中间;四个字及以上的脱敏前两个以后的;
            int nameLength = 0;
            nameLength = StringUtils.length(fullName);
            String tempName ="";
            String tempName2 = "";
            if (nameLength != 0){
                if (nameLength == 2){
                    tempName = StringUtils.left(fullName, 1);
                    result = StringUtils.rightPad(tempName, StringUtils.length(fullName), "*");
                }else if (nameLength == 3){
                    tempName = StringUtils.left(fullName, 1);
                    tempName2 = StringUtils.right(fullName, 1);
                    result = tempName+"*"+tempName2;
                }else {
                    if (nameLength > 3){
                        tempName = StringUtils.left(fullName, 2);
                        result = StringUtils.rightPad(tempName, StringUtils.length(fullName), "*");
                    }
                }
            }
        }
        return result;
    }
    /**
    *
    * @Description [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例：张**>
    * @Param  [familyName, givenName] 姓氏，名字
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:14 2019/4/10
    **/
    public static String chineseName(String familyName, String givenName) {
        String result = "";
        if (StringUtils.isNotBlank(familyName) || StringUtils.isNotBlank(givenName)) {
            result = chineseName(familyName + givenName);
        }
        return result;
    }

    /**
    *
    * @Description [身份证号] 123****12，前面保留3位明文，后面保留2位明文
    * @Param  [id]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:16 2019/4/10
    **/
    public static String identificationNum(String idcard) {
        String result = "";
        if (StringUtils.isNotBlank(idcard)) {
            result = StringUtils.left(idcard, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idcard, 2), StringUtils.length(idcard), "*"), "***"));
        }
        return result;
    }

    /**
    *
    * @Description [固定电话] 后四位，其他隐藏<例：****1234>
    * @Param  [num]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:18 2019/4/10
    **/
    public static String fixedPhone(String num) {
        String result = "";
        if (StringUtils.isNotBlank(num)) {
            result = StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
        }
        return result;
    }

    /**
    *
    * @Description [手机号码] 前3位，后4位，其他隐藏<例:123****1234>
    * @Param  [num]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   10:06 2019/4/10
    **/
    public static String mobilePhone(String num) {
        String result = "";
        if (StringUtils.isNotBlank(num)) {
            result = StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));
        }
        return result;
    }

   /**
   *
   * @Description [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例：北京市海淀区****>
   * @Param  [address, sensitiveSize]  [地址，敏感字段长度]
   * @Returu java.lang.String
   * @Author zhrb
   * @Date   9:20 2019/4/10
   **/
    public static String address(String address, int sensitiveSize) {
        String result = "";
        if (StringUtils.isNotBlank(address)) {
            int length = StringUtils.length(address);
            result = StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
        }
        return result;
    }

    /**
    *
    * @Description [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例:g**@163.com>
    * @Param  [email]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:20 2019/4/10
    **/
    public static String email(String email) {
        String result = "";
        if (StringUtils.isNotBlank(email)) {
            int index = StringUtils.indexOf(email, "@");
            if (index <= 1){
                result = email;
            }else {
                result = StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
            }
        }
        return result;
    }

    /**
    *
    * @Description [银行卡号] 前四位，后四位，其他用星号隐藏每位1个星号<例:6222600**********1234>
    * @Param  [cardNum]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:21 2019/4/10
    **/
    public static String bankCard(String cardNum) {
        String result = "";
        if (StringUtils.isNotBlank(cardNum)) {
            result = StringUtils.left(cardNum, 4).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
        }
        return result;
    }

    /**
    *
    * @Description [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例:12********>
    * @Param  [code]
    * @Returu java.lang.String
    * @Author zhrb
    * @Date   9:22 2019/4/10
    **/
    public static String cnapsCode(String code) {
        String result = "";
        if (StringUtils.isNotBlank(code)) {
            result = StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");
        }
        return result;
    }
}
