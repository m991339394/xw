package io.renren.modules.app.model.form;

import io.renren.modules.app.model.po.HCardOrderPO;
import lombok.Data;

/**
 * @ClassName HCardOrderForm
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/2 17:55
 * @Version 1.0
 */
@Data
public class HCardOrderForm extends HCardOrderPO {
    private String type;
}
