package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardPricePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
@Mapper
public interface UserHCardPriceDao extends BaseMapper<HCardPricePO> {

	int increaswHCardCount(HCardPricePO hCardPricePO);
	
	int subHCardCount(HCardPricePO hCardPricePO);
}
