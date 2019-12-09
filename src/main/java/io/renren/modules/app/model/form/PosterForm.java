package io.renren.modules.app.model.form;

import lombok.Data;

/**
 * @ClassName PosterForm
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/9 11:21
 * @Version 1.0
 */
@Data
public class PosterForm extends QRCodeForm{
    //海报图
    private String backgroundImageUrl;
    //二维码
    private String qrCodeImageUrl;
    //头像
    private String faceIcon;
    //昵称
    private String nickName;
    // 海报图说明
    private String remark;
    // 昵称后的短语
    private String nameText;
    // 公司宣传语
    private String cqText;
}
