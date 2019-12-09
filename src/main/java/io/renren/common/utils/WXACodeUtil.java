package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.config.PayConfig;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDate.now;

/**
 * @ClassName WXACode
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/6 9:34
 * @Version 1.0
 */
@RestController
public class WXACodeUtil extends BaseController {
    private static String grant_type = "client_credential";
    private static String appid = PayConfig.APPID2;
    private static String secret = PayConfig.APP_SECRET2;
    private static String access_token = "28_SCv1M4r8jJRhDwJaMOAMQDMyFa9GLzODUl-yKbrSfEd_R6iVJwcIvkRLYsNA8B-S86QDH-4sALviV9gS3Xw_1TYMiCTuieUgfn7QWkZ8zeFo4r06UQdSsS_wdCp3-ZyRMUl4m8LDgz8_O90HYRVcAIAGZD";

    public static String getAccessToken() {
        String param = "grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret;
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String result = HttpRequest.sendGet(url, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    public static String getWXACode(Object obj) {
        String access_token = getAccessToken();
//        map.put("path" ,"pages/store/purchase/purchase?id=1");
//        map.put("width" ,430);
//        map.put("auto_color" ,true);
//        map.put("line_color" ,"{\"r\":0,\"g\":0,\"b\":0}");
//        map.put("is_hyaline" ,true);
        String param = JSONObject.toJSONString(obj);
        String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + access_token;
        String dirPath = StaticUtil.SAVE_QR_CODE_DIR ;
        String fileName = UUID.randomUUID() + ".jpg";
        String newPath = dirPath+now()+File.separator+fileName ;  // /pro/pic/jpg
        String savePath = StaticUtil.SAVE_URL_LINUX +newPath ;  // /pro/pic/jpg
        File saveFile = new File(savePath);
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }
        String result = HttpRequest.sendPost2(url, param ,savePath);
        return StaticUtil.STATIC_URL+newPath;
    }

    public static String getWXACodeUnlimited() {
//        String access_token = getAccessToken();
        Map<String, Object> map = new HashMap();
        map.put("scene", "pages/store/purchase/purchase");
        map.put("width", 430);
        map.put("auto_color", true);
        map.put("line_color", "{\"r\":0,\"g\":0,\"b\":0}");
        map.put("is_hyaline", true);
        String param = JSONObject.toJSONString(map);
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + access_token;
        String result = HttpTool.sendPostJSON(url, param);

        return result;
    }

    public static String createQRCode() {
//        String access_token = getAccessToken();
        Map<String, Object> map = new HashMap();
        map.put("path", "pages/store/purchase/purchase");
        map.put("width", 430);
        String param = JSONObject.toJSONString(map);
        String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + access_token;
        String result = HttpTool.sendPostJSON(url, param);

        return result;
    }

}
