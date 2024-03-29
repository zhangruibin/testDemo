package com.zhrb.util;


import com.alibaba.druid.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EncodePwdTool {

    private static final String HEX_NUMS_STR="0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;

    private static Map<String ,UserTestEntity>  map= new HashMap<>();

    /**
     * 将16进制字符串转换成字节数组
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 验证口令是否合法
     * @param password
     * @param passwordInDb
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validPassword(String password, String passwordInDb,String salt,String algorithm)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //验证密码是否存在
        if(StringUtils.isEmpty(password)|| StringUtils.isEmpty(passwordInDb)|| StringUtils.isEmpty(salt)|| StringUtils.isEmpty(algorithm)){
            return false;
        }
        //将16进制字符串格式口令转换成字节数组
        byte[] pwdInDb = hexStringToByte(passwordInDb);
        //声明盐变量
//        byte[] salt = new byte[SALT_LENGTH];
        //将盐从数据库中保存的口令字节数组中提取出来
//        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        //创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance(algorithm);
        //将盐数据传入消息摘要对象
        md.update(hexStringToByte(salt));
        //将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));
        //生成输入口令的消息摘要
        byte[] digest = md.digest();
        //声明一个保存数据库中口令消息摘要的变量
//        byte[] digestInDb = new byte[pwdInDb.length];
        //取得数据库中口令的消息摘要
//        System.arraycopy(pwdInDb, 0, digestInDb, 0, digestInDb.length);
        //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
        if (Arrays.equals(digest, pwdInDb)) {
            //口令正确返回口令匹配消息
            return true;
        } else {
            //口令不正确返回口令不匹配消息
            return false;
        }
    }

    public static String  getSalt(String password){
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量   12
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);
        return byteToHexString(salt);
    }
    /**
     * 获得加密后的16进制形式密码
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getEncryptedPwd(String password,String salt,String algorithm)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //声明加密后的口令数组变量
//        byte[] pwd = null;

        //声明消息摘要对象
        MessageDigest md = null;
        //创建消息摘要
        md = MessageDigest.getInstance(algorithm);
        //将盐数据传入消息摘要对象
        md.update(hexStringToByte(salt));
        //将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));
        //获得消息摘要的字节数组 加密后的密
        byte[] digest = md.digest();

        //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
//        pwd = new byte[digest.length];
        //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
//        System.arraycopy(getSalt(password), 0, pwd, 0, SALT_LENGTH);
        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
//        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
//        for(int i=0;i<pwd.length;i++){
//            System.out.print(pwd[i]);
//        }
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(digest);
    }
// 下面用于测试 加密正确性。不建议使用
    @Deprecated
    public static void main(String[] args){
        String algorithm ="MD5";
        String userName = "zyg";
        String password = "123";
        registerUser(userName,password,algorithm);

        userName = "changong";
        password = "456";
        registerUser(userName,password,algorithm);

         userName = "zyg";
        String pwd = "123";
        try {
            if(loginValid(userName,pwd,algorithm)){
                System.out.println("欢迎登陆！！！");
            }else{
                System.out.println("口令错误，请重新输入！！！");
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 注册用户
     *
     * @param userName
     * @param password
     */
    @Deprecated
    public static void registerUser(String userName,String password,String algorithm){
        String encryptedPwd = null;
        try {
            UserTestEntity userEntity = new UserTestEntity();
            userEntity.setUserName(userName);
            userEntity.setPassword(password);
            userEntity.setSalt(getSalt(password));
            encryptedPwd = EncodePwdTool.getEncryptedPwd(password,userEntity.getSalt(),algorithm);
            userEntity.setEncodePwd(encryptedPwd);
            map.put(userName,userEntity);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 验证登陆
     *
     * @param userName
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Deprecated
    public static boolean loginValid(String userName,String password,String algorithm)
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        UserTestEntity userEntity = map.get(userName);
        if(null!=userEntity){ // 该用户存在
            return EncodePwdTool.validPassword(password, userEntity.getEncodePwd(),userEntity.getSalt(),algorithm);
        }else{
            System.out.println("不存在该用户！！！");
            return false;
        }
    }
}