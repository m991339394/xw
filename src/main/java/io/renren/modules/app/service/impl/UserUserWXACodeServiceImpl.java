package io.renren.modules.app.service.impl;

import io.renren.common.utils.ImageUtil;
import io.renren.common.utils.WXACodeUtil;
import io.renren.modules.app.model.form.PosterForm;
import io.renren.modules.app.model.form.QRCodeForm;
import io.renren.modules.app.service.UserUserWXACodeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserUserWXACodeServiceImpl
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/6 18:51
 * @Version 1.0
 */
@Service
public class UserUserWXACodeServiceImpl  implements UserUserWXACodeService {
    @Override
    public String getAppQRCode(QRCodeForm qrCodeForm) {
        Map map=new HashMap();
        map.put("path" ,qrCodeForm.getPath()+"&userId="+qrCodeForm.getUserId());
        return WXACodeUtil.getWXACode(map);
    }

    @Override
    public String getPoster(PosterForm posterForm) {
        try {
            String url=ImageUtil.drawImage(posterForm);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
