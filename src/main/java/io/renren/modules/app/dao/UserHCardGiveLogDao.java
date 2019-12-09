package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardGiveLogPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡转赠记录表 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-10-24
 */
@Mapper
public interface UserHCardGiveLogDao extends BaseMapper<HCardGiveLogPO> {
	
}
