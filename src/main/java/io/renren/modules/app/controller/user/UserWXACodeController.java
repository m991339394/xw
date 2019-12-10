package io.renren.modules.app.controller.user;

import io.renren.common.Result;
import io.renren.modules.app.model.form.PosterForm;
import io.renren.modules.app.model.form.QRCodeForm;
import io.renren.modules.app.service.UserUserWXACodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WXACode
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/6 17:33
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/user/QRCode")
public class UserWXACodeController {
    @Autowired
    UserUserWXACodeService wxaCodeService;

    /**
     * @Author jgl
     * @Description
     * @Date 17:34 2019/12/6
     * @Param [openid]
     * @return io.renren.common.Result<?>
     **/
    @RequestMapping(value = "/getAppQRCode.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> getAppQRCode(QRCodeForm qrCodeForm) throws Exception {
        String url=wxaCodeService.getAppQRCode(qrCodeForm);
        return Result.success(url);
    }

    /**
     * @Author jgl
     * @Description
     * @Date 17:34 2019/12/6
     * @Param [openid]
     * @return io.renren.common.Result<?>
     **/
    @RequestMapping(value = "/getPoster.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<?> getPoster(PosterForm posterForm) throws Exception {

        QRCodeForm qrCodeForm=new QRCodeForm();
        qrCodeForm.setPath(posterForm.getPath());
        qrCodeForm.setPath(posterForm.getUserId());
        String qrCodeImageUrl=wxaCodeService.getAppQRCode(qrCodeForm);
        posterForm.setNameText("送给你一个礼物");
        posterForm.setCqText("快来扫码领取礼物吧，更多祝福都在我的礼品之家");
        posterForm.setQrCodeImageUrl(qrCodeImageUrl);
//        posterForm.setQrCodeImageUrl("https://flowAdmin.834445.net/file/images/qrCode/2019-12-09/95ef21b7-bc94-4b6e-b4c7-c6a817f7b71a.jpg");
        String url=wxaCodeService.getPoster(posterForm);
        return Result.success(url);
    }

}
