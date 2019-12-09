package io.renren.modules.app.dao;

import io.renren.modules.app.model.po.HCardTypePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡类型 Mapper 接口
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@Mapper
public interface AdminHCardTypeDao extends BaseMapper<HCardTypePO> {

}
