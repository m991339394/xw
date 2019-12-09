package io.renren.modules.flow.dao;

import io.renren.modules.flow.model.po.FlowTaskPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 任务 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-12-03
 */
@Mapper
public interface FlowTaskDao extends BaseMapper<FlowTaskPO> {

}
