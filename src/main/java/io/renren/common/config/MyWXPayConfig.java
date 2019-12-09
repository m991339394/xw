package io.renren.common.config;


import io.renren.modules.app.service.thirdParty.wxSdk.IWXPayDomain;
import io.renren.modules.app.service.thirdParty.wxSdk.WXPayConfig;
import io.renren.modules.app.service.thirdParty.wxSdk.WXPayConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @ClassName MyConfig
 * @Description TODO
 * @Author jgl
 * @Date 2019/6/7 18:34
 * @Version 1.0
 */
@Service
public class MyWXPayConfig extends WXPayConfig {
    private byte[] certData;

//    public MyWXPayConfig() throws Exception {
//        String certPath = "classpath:/apiclient_cert.p12";
////        File file = new File(certPath);
//        File file = ResourceUtils.getFile(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
//    }
//    public MyWXPayConfig() throws Exception {
//        String certPath = "classpath:apiclient_cert.p12";
//        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(certPath);
//        this.certData = IOUtils.toByteArray(certStream);
//        certStream.close();
//    }
    public MyWXPayConfig() throws Exception {
        String certPath = "apiclient_cert.p12";
        ClassPathResource cpr=new ClassPathResource(certPath);
        this.certData = FileCopyUtils.copyToByteArray(cpr.getInputStream());
    }

    @Override
    public String getAppID() {
        return PayConfig.APPID;
    }

    @Override
    public String getMchID() {
        return PayConfig.MMCH_ID;
    }

    @Override
    public String getKey() {
        return PayConfig.KEY;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                String domain = WXPayConstants.DOMAIN_API;       //域名
                boolean primaryDomain=true;     //该域名是否为主域名。例如:api.mch.weixin.qq.com为主域名
                DomainInfo domainInfo=new DomainInfo(domain ,primaryDomain);
                return domainInfo;
            }
        };
    }
}
