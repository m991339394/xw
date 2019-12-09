package io.renren.common.utils;

import org.springframework.util.DigestUtils;

public class Md5Encrypt {
	public static String md5Encrypt(String old){
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(old.getBytes());
		//加密结果倒转
		String middle=new StringBuilder(md5DigestAsHex).reverse().toString();
		//第二次加密
		String result = DigestUtils.md5DigestAsHex(middle.getBytes());
		return result;
	}
}
