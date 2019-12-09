package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户余额/积分变动纪录表 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@Mapper
public interface UserUserHCardBalanceLogDao extends BaseMapper<UserHCardBalanceLogPO> {
	
	List<UserHCardBalanceLogPO> getUserHCardBalanceLogByUseStatus(Map map);
}
