package io.renren.modules.app.model.vo;

import io.renren.modules.app.model.po.ExpressDeliveryPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName ExpressDeliveryVO
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/4 16:14
 * @Version 1.0
 */
@Data
public class ExpressDeliveryVO extends ExpressDeliveryPO {

    @ApiModelProperty(value = "心意卡名称")
    private String hCardName;

    @ApiModelProperty(value = "心意卡卡面图url")
    private String img;


}
