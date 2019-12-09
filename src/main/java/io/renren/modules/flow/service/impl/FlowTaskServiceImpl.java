package io.renren.modules.flow.service.impl;

import io.renren.modules.flow.model.po.FlowTaskPO;
import io.renren.modules.flow.dao.FlowTaskDao;
import io.renren.modules.flow.service.FlowTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-12-03
 */
@Service
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskDao, FlowTaskPO> implements FlowTaskService {

}
