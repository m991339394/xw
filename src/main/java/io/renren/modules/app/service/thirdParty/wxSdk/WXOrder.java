package io.renren.modules.app.service.thirdParty.wxSdk;

import io.renren.common.Result;
import io.renren.common.config.PayConfig;
import io.renren.common.exception.RRException;
import io.renren.common.utils.IPUtils;
import io.renren.common.utils.PayUtil;
import io.renren.common.utils.StringUtils;
import io.renren.modules.app.model.form.HCardOrderForm;
import io.renren.modules.app.model.po.HCardOrderPO;
import io.renren.modules.app.service.UserHCardOrderService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WXOrder
 * @Description 发起微信支付,统一订单
 * @Author jgl
 * @Date 2019/5/25 20:00
 * @Version 1.0
 */
@Service
public class WXOrder {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserHCardOrderService userHCardOrderService;
    @Autowired
    WXPayConfig config;

    /**
     * @Author jgl
     * @Description 发起微信支付,统一订单
     * @Date 18:10 2019/11/25
     * @Param [request, hCardOrderPO]
     * @return java.util.Map
     **/
    public Map wxPay(HttpServletRequest request
                      , HCardOrderForm hCardOrderForm){
    	Map json=myUnifiedOrder(request ,hCardOrderForm);
//        Json json=unifiedOrder(request ,order);
        return json;
    }


    public Map myUnifiedOrder(HttpServletRequest request
            , HCardOrderForm hCardOrderForm){

        String type=hCardOrderForm.getType();
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        HCardOrderPO hCardOrderPO = dozerBeanMapper.map(hCardOrderForm ,HCardOrderPO.class);
    	Map json = new HashMap();
        try{
            String appid;
            if("2".equals(type)){
                appid = PayConfig.APPID2;
            }else{
                appid = PayConfig.APPID;
            }

            String mch_id = PayConfig.MMCH_ID;
            // 生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            // 商品描述
            String body = hCardOrderPO.getBody();
            // 商品订单号
            String out_trade_no = StringUtils.getRandomStringByLength(30);
            // 支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
            String total_fee = (int)(hCardOrderPO.getTotalFee()*100)+"";
            // 获取本机的ip地址
            String spbill_create_ip = IPUtils.getIpAddr(request);
            // 支付成功后的服务器回调url
            String notify_url = PayConfig.NOTIFY_URL;
            // 交易类型
            String trade_type = PayConfig.TRADETYPE;
            // 用户openid
            String openid = hCardOrderPO.getOpenid();
            // 签名方式
            String signType = PayConfig.SIGNTYPE;
            //微信支付的商户密钥
            String key = PayConfig.KEY;

            Map<String, String> packageParams = new HashMap();
            packageParams.put("appid", appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", out_trade_no);
            packageParams.put("total_fee", total_fee);
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", notify_url);
            packageParams.put("trade_type", trade_type);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);

            // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String prestr = PayUtil.createLinkString(packageParams);

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + total_fee + "</total_fee>"
                    + "<trade_type>" + trade_type + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(PayConfig.UNIFIEDORDER, "POST", xml);

            System.out.println("调试模式_统一下单接口 返回的结果：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            Map<String, String> response = new HashMap();
            if(return_code == "SUCCESS" || return_code.equals(return_code)){
                // 业务逻辑代码start
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                Long timeStamp = System.currentTimeMillis() / 1000;
                String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=" + signType + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                response.put("signType", signType);
                response.put("paySign", paySign);
                response.put("out_trade_no", out_trade_no);
                
                // 创建订单信息
                // 业务逻辑代码end
            }
            
            json.put("order", packageParams);
            json.put("data", response);
            json.put("code", 0);
            json.put("msg", "success");

        }catch(Exception e){
            e.printStackTrace();
            json.put("code", 1);
            json.put("msg","发起失败");
        }
        return json;
    }



    public Result unifiedOrder(HttpServletRequest request
            , @RequestBody HCardOrderPO hCardOrderPO){

    	Result json = new Result();
        try {
            Map<String, String> result ;
            WXPay wxpay  = new WXPay(config);

            // 商品描述
            String appid = PayConfig.APPID;
            // 商品描述
            String mch_id = PayConfig.MMCH_ID;
            // 生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            // 商品描述
            String body = hCardOrderPO.getBody();
            // 商品订单号
            String out_trade_no = StringUtils.getRandomStringByLength(30);
            // 支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
            String total_fee = (int)(hCardOrderPO.getTotalFee()*100)+"";
            // 获取本机的ip地址
            String spbill_create_ip = IPUtils.getIpAddr(request);
            // 用户openid
            String notify_url = PayConfig.NOTIFY_URL;
            // 用户openid
            String trade_type = PayConfig.TRADETYPE;
            // 用户openid
            String openid = hCardOrderPO.getOpenid();
            // 签名方式
            String signType = WXPayConstants.MD5.equals(wxpay.getSignType())?WXPayConstants.MD5:WXPayConstants.HMACSHA256;
            //微信支付的商户密钥
            String key = PayConfig.KEY;

            Map<String, String> data = new HashMap<String, String>();
            data.put("appid", appid);
            data.put("mch_id", mch_id);
            data.put("nonce_str", nonce_str);
            data.put("body", body);
            data.put("out_trade_no", out_trade_no);
            data.put("total_fee", total_fee);
            data.put("spbill_create_ip", spbill_create_ip);
            data.put("notify_url", notify_url);
            data.put("trade_type", trade_type);
            data.put("openid", openid);
            // 调取统一下单接口
            result = wxpay.unifiedOrder(data);
            
            System.out.println(result);
            Map<String, String> response = new HashMap();
            String prepay_id=result.get("prepay_id");
            Long timeStamp = System.currentTimeMillis() / 1000;
            String paySign = WXPayUtil.generateSignature(response ,key, wxpay.getSignType());
            response.put("appId", appid);
            response.put("nonceStr", nonce_str);
            response.put("package", "prepay_id=" + prepay_id);
            response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
            response.put("signType", signType);
            response.put("paySign", paySign);
            response.put("out_trade_no", out_trade_no);
            json.setCode(0);
            json.setData(response);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode(1);
            json.setMsg("发起失败");
        }
        return  json;
    }


    /**
     * @Author jgl
     * @Description 订单查询
     * @Date 11:38 2019/9/19
     * @Param [out_trade_no]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    @RequestMapping(value = "/orderQuery.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> orderQuery(@RequestParam(value = "out_trade_no") String out_trade_no){
        try {
            WXPay wxPay=new WXPay(config);
            Map<String, String> data=new HashMap<>();
            data.put("out_trade_no" ,out_trade_no);
            return wxPay.orderQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * @Author jgl
     * @Description 关闭订单
     * @Date 11:38 2019/9/19
     * @Param [out_trade_no]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    @RequestMapping(value = "/closeOrder.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> closeOrder(@RequestParam(value = "out_trade_no") String out_trade_no){
        try {
            WXPay wxPay=new WXPay(config);
            Map<String, String> data=new HashMap<>();
            data.put("out_trade_no" ,out_trade_no);
            return wxPay.closeOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
    
    public Map<String, String> cancelHCardOrder(String out_trade_no){
        try {
            WXPay wxPay=new WXPay(config);
            Map<String, String> data=new HashMap<>();
            data.put("out_trade_no" ,out_trade_no);
            return wxPay.closeOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * @Author jgl
     * @Description 申请退款
     * @Date 19:28 2019/9/29
     * @Param [out_trade_no]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    @RequestMapping(value = "/refund.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor=Exception.class)
    public Map<String, String> refund(@RequestParam(value = "out_trade_no") String out_trade_no
                                        ,@RequestParam(value = "openid") String openid
                                        ,@RequestParam(value = "refund_fee") Integer refund_fee
                                        ,@RequestParam(value = "total_fee") Integer total_fee){
        try {
            WXPay wxPay=new WXPay(config);
            Map<String, String> data=new HashMap<>();
            String out_refund_no = StringUtils.getRandomStringByLength(32);
            data.put("out_trade_no" ,out_trade_no);
            data.put("out_refund_no" ,out_refund_no);
            data.put("refund_fee" ,refund_fee.toString());
            data.put("total_fee" ,total_fee.toString());
            return wxPay.refund(data);
        } catch (Exception e) {
            e.printStackTrace();
         // 抛出异常
			throw new RRException("退款失败" + "异常,"+" out_trade_no ="+out_trade_no + "openid=" + openid + "refund_fee=" + refund_fee);
        }
    }

    /**
     * @Author jgl
     * @Description 查询退款
     * @Date 12:12 2019/9/19
     * @Param [out_trade_no]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    @RequestMapping(value = "/refundQuery.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> refundQuery(@RequestParam(value = "out_trade_no") String out_trade_no){
        try {
            WXPay wxPay=new WXPay(config);
            Map<String, String> data=new HashMap<>();
            data.put("out_trade_no" ,out_trade_no);
            return wxPay.refundQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * @Author jgl
     * @Description 下载对账单
     * @Date 15:10 2019/9/19
     * @Param [out_trade_no]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    @RequestMapping(value = "/downloadBill.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> downloadBill(@RequestParam(value = "bill_date") String bill_date){
        try {
            WXPay wxPay=new WXPay(config);
            Map<String, String> data=new HashMap<>();
            data.put("bill_date", bill_date);
            data.put("bill_type", "ALL");
            return wxPay.downloadBill(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * @Author jgl
     * @Description 下载资金账单
     * @Date 15:18 2019/9/30
     * @Param [bill_date, account_type]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    @RequestMapping(value = "/downloadfundflow.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> downloadfundflow(@RequestParam(value = "bill_date") String bill_date
                                                ,@RequestParam(value = "account_type") String account_type){
        try {
            WXPay wxPay=new WXPay(config);
            Map<String, String> data=new HashMap<>();
            data.put("bill_date" ,bill_date);
            data.put("account_type" ,account_type);
            return wxPay.downloadfundflow(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

}
