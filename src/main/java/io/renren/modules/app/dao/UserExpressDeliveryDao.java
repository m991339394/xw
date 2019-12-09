package io.renren.modules.app.dao;

import io.renren.modules.app.model.po.ExpressDeliveryPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 快递单号 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-11-26
 */
@Mapper
public interface UserExpressDeliveryDao extends BaseMapper<ExpressDeliveryPO> {

}
