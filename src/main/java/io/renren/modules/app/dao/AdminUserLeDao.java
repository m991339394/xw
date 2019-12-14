package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.LePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-12-13
 */
@Mapper
public interface AdminUserLeDao extends BaseMapper<LePO> {

}
