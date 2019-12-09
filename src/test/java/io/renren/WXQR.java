package io.renren;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.WXACodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName StringTest
 * @Description TODO
 * @Author jgl
 * @Date 2019/6/2 16:33
 * @Version 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class WXQR {
    @Autowired
    WXACodeUtil wxaCode;

    @Test
    public void test() {
//        String result = wxaCode.getAccessToken();
//        System.out.println("----------------result---------------"+result);
//        String getWXACode = wxaCode.getWXACode();
//        System.out.println("-----------------getWXACode--------------------" + getWXACode);

//        String imgStr = getWXACode;
//        String imgPath = "D:/pic";
//        String imgName = "img.jpg";
//        int num = ImgErToFileUtil.saveToImgByStr(imgStr, imgPath, imgName);


//        String getWXACodeUnlimited = wxaCode.getWXACodeUnlimited();
//        System.out.println("-----------------getWXACodeUnlimited--------------------"+getWXACodeUnlimited);
//
//        String createQRCode = wxaCode.createQRCode();
//        System.out.println("-----------------createQRCode--------------------"+createQRCode);
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap();
        map.put("page", "page/index/index");

        System.out.println(map);
        System.out.println(JSONObject.toJSONString(map));
    }

}
