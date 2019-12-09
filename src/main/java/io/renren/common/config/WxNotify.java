package io.renren.common.config;

import io.renren.common.utils.PayUtil;
import io.renren.modules.app.service.UserHCardOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
/**
 * 微信支付成功后的通知
 * @author Administrator
 */
@RestController
@RequestMapping(value="/api/user")
public class WxNotify {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    UserHCardOrderService userHCardOrderService;

    /**
     * @Description:微信支付成功后的通知
     * @return
     * @author dzg
     * @throws Exception
     * @date 2018年7月17日
     */
    @RequestMapping(value="/wxNotify.do")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
//        System.out.println("接收到的报文：" + notityXml);
        logger.info("=======================接收到的报文：" + notityXml + "=====================");

        Map<String ,String> map = PayUtil.doXMLParse(notityXml);

        String returnCode = map.get("return_code");
        
        String sign=map.get("sign");
        
        map.remove("sign");
        
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            if(PayUtil.verify(PayUtil.createLinkString(map), sign, PayConfig.KEY, "utf-8")){
                /**此处添加自己的业务逻辑代码start**/
            	String out_trade_no=(String)map.get("out_trade_no");
            	logger.info("=======================out_trade_no====================="+out_trade_no);
            	userHCardOrderService.successfulPaymentNotify(out_trade_no);
                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
//        System.out.println(resXml);
//        System.out.println("微信支付回调数据结束");
        logger.info("=======================：" + resXml + "=====================");
        logger.info("=======================微信支付回调数据结束=====================");
        
        BufferedOutputStream out = new BufferedOutputStream(
        response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}
