package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.UserPO;
import io.renren.modules.app.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-10-05
 */
@Mapper
public interface UserUserDao extends BaseMapper<UserPO> {
	UserVO findByOpenId(String openid);
}
