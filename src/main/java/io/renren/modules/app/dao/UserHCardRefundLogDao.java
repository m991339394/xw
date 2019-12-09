package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardRefundLogPO;
import io.renren.modules.app.model.po.UserHCardPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 心意卡转赠记录表 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-11-19
 */
@Mapper
public interface UserHCardRefundLogDao extends BaseMapper<HCardRefundLogPO> {
	
	List<UserHCardPO> getrRefundRecord(Map map);

}
