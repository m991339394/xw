package io.renren.modules.app.service;

import io.renren.modules.app.model.po.ExpressDeliveryPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 快递单号 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-11-26
 */
public interface UserExpressDeliveryService extends IService<ExpressDeliveryPO> {

    ExpressDeliveryPO getExpressDeliveryByHCardId(Long hCard);

}
