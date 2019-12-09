package io.renren.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;

public class UUIDTool {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * @return 订单号
	 */
	public static String getOrderNumber() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate = sdf.format(new Date());// 时间戳
		int randomNumber = (int) ((Math.random() * 9 + 1) * 1000); // 随机数
		String pay_orderid = randomNumber + newDate;// 订单号
		return pay_orderid;
	}

	// 产生1000-10000的随机数
	/**
	 * @return
	 */
	public static String getRandom() {
		int max = 10000;
		int min = 1000;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s + "";
	}
}
