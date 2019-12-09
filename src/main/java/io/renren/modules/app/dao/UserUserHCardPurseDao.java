package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.UserHCardPursePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户心意卡钱包 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@Mapper
public interface UserUserHCardPurseDao extends BaseMapper<UserHCardPursePO> {
	int recharge(UserHCardPursePO userHCardPursePO);
}
