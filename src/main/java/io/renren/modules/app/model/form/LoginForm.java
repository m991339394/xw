package io.renren.modules.app.model.form;

import lombok.Data;

/**
 * @ClassName LoginForm
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/2 17:36
 * @Version 1.0
 */
@Data
public class LoginForm {
    private String code;
    private String nickName;
    private String faceIcon;
    private String type;
}
