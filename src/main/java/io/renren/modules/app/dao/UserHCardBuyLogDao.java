package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardBuyLogPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡购买记录表 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-11-21
 */
@Mapper
public interface UserHCardBuyLogDao extends BaseMapper<HCardBuyLogPO> {

}
