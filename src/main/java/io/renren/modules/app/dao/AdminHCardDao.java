package io.renren.modules.app.dao;

import io.renren.modules.app.model.po.HCardPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡 Mapper 接口
 * </p>
 *
 * @author cth
 * @since 2019-10-12
 */
@Mapper
public interface AdminHCardDao extends BaseMapper<HCardPO> {

}
