package io.renren.modules.app.model.form;

import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.app.model.po.ExpressDeliveryPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 快递单号
 * </p>
 *
 * @author jgl
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("express_delivery")
@ApiModel(value="ExpressDeliveryPO对象", description="快递单号")
public class ExpressDeliveryForm extends ExpressDeliveryPO {

    @ApiModelProperty(value = "用户心意卡id")
    private Long hCardId;

    private String uuid;


}
