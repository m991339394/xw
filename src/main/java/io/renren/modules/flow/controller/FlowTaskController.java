package io.renren.modules.flow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.Constants.HCardTypeConstants;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.flow.model.po.FlowTaskPO;
import io.renren.modules.flow.service.FlowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 任务 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/api/flow/task")
public class FlowTaskController extends BaseController {
    @Autowired
    FlowTaskService taskService;

    @RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> list(){
        try {
            QueryWrapper<FlowTaskPO> qWrapper=new QueryWrapper();
            qWrapper.eq("upper_shelf", HCardTypeConstants.UPPER_SHELF);
            List<FlowTaskPO> list=taskService.list(qWrapper);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500,e.getMessage());
        }
    }

}
