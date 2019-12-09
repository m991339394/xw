package io.renren.modules.app.dao;

import io.renren.modules.app.model.po.HCardPricePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡价格 Mapper 接口
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@Mapper
public interface AdminHCardPriceDao extends BaseMapper<HCardPricePO> {

}
