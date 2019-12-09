package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.po.HCardConsumeRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 心意卡消费记录 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-10-04
 */
@Mapper
public interface UserHCardConsumeRecordDao extends BaseMapper<HCardConsumeRecordPO> {

}
