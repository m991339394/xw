package io.renren.modules.app.service;

import io.renren.modules.app.model.form.PosterForm;
import io.renren.modules.app.model.form.QRCodeForm;

/**
 * @ClassName UserUserWXACodeService
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/6 18:38
 * @Version 1.0
 */
public interface UserUserWXACodeService {

    String getAppQRCode(QRCodeForm qrCodeForm);

    String getPoster(PosterForm posterForm);
}
