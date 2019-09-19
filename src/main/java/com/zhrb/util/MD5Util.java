package com.zhrb.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class MD5Util {
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			//e.printStackTrace();
			//zhrb-20190528-修复漏洞
			log.info("This function throws an exception.This's the exception detail:{}", e);
			return null;
		}
	}

	// public static void main(String[] args) {
	// // System.out.println(MD5Util.MD5("123456").toLowerCase());
	// // ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]
	// String regex =
	// "^(?![0-9]+$)(?![a-zA-Z]+$)(?![#@*&.!$%(){}\\]\\[:;]+$)[0-9A-Za-z#@*&.!$%(){}\\]\\[:;]{8,}$";
	// String regex2 = "(.+)?\\d(.+)?";
	// String regex3 = "(.+)?[a-zA-Z](.+)?";
	// String regex4 = "(.+)?[#@*&.!$%(){}\\]\\[:;](.+)?";
	// String value = "aaaaaaaaaaaaaa";
	// System.out.println(value.matches(regex));
	// System.out.println(value.matches(regex2));
	// System.out.println(value.matches(regex3));
	// System.out.println(value.matches(regex4));
	//
	// }
}