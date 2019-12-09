package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardOrderPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户购买心意卡订单记录 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
@Mapper
public interface UserHCardOrderDao extends BaseMapper<HCardOrderPO> {
	
	HCardOrderPO getHCardOrderByOutTradeNo(String outTradeNo);

}
