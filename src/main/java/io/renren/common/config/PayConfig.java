package io.renren.common.config;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Date: 2018/7/17
 * @Author: wcf
 */
@Component
public final class PayConfig {
	// 旅游卡appid
    public static final String APPID = "wxce392efeec0169a9";
    // 旅游卡secret
    public static final String APP_SECRET = "2f990862d916fc6c85fb64e6986caf87";
	// 礼品之家appid
    public static final String APPID2= "wxeb58125a331dfc6c";
    // 礼品之家secret
    public static final String APP_SECRET2 = "7429a5abe59c36bf2f841fd37988dd46";
    //微信支付的商户id
    public static final String MMCH_ID= "1518792851";
    //微信支付的商户密钥
    public static final String KEY = "123456qwertyuiopasdfghjklzxcvbnm";
    //支付成功后的服务器回调url
    public static final String NOTIFY_URL = "https://flow.834445.net/flow/api/user/wxNotify.do";
    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //查询订单
    public static final String ORDERQUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

    // 沙箱 key  pay_url
//    public static final String KEY = "37de0b452d1d68b79bce7b121759a95f";
//    public static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";




}
