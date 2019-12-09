package io.renren.common.utils;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Date: 2018/7/17
 * @Author: wcf
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
    /**
     * StringUtils工具类方法
     * 获取一定长度的随机字符串，范围0-9，a-z
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    
    public static  String listToString(List<Integer> list) {
    	StringBuffer idsStringBuffer=new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			idsStringBuffer.append(list.get(i));
			idsStringBuffer.append(",");
		}
		String idsString=idsStringBuffer.toString();
    	return idsString;
    }
}
