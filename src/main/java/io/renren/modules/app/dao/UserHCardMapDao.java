package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardMapPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡卡面图 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-09-27
 */
@Mapper
public interface UserHCardMapDao extends BaseMapper<HCardMapPO> {

}
