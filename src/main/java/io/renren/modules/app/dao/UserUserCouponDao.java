package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.UserCouponPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户的心意卡优惠券 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-10-19
 */
@Mapper
public interface UserUserCouponDao extends BaseMapper<UserCouponPO> {

}
