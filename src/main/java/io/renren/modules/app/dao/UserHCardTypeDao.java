package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡类型 Mapper 接口
 * </p>
 *
 * @author cth
 * @since 2019-09-27
 */
@Mapper
public interface UserHCardTypeDao extends BaseMapper<HCardTypePO> {

}
