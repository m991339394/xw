package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardHelpPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 帮助中心
 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-10-18
 */
@Mapper
public interface UserHCardHelpDao extends BaseMapper<HCardHelpPO> {

}
