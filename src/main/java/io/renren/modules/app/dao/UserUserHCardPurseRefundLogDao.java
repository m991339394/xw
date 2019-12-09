package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.UserHCardPurseRefundLogPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡余额退款记录表 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-11-22
 */
@Mapper
public interface UserUserHCardPurseRefundLogDao extends BaseMapper<UserHCardPurseRefundLogPO> {

}
