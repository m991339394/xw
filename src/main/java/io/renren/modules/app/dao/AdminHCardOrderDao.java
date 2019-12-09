package io.renren.modules.app.dao;

import io.renren.modules.app.model.po.HCardOrderPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户购买心意卡订单记录 Mapper 接口
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@Mapper
public interface AdminHCardOrderDao extends BaseMapper<HCardOrderPO> {

}
