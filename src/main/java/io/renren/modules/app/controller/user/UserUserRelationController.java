package io.renren.modules.app.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.UserUserRelationPO;
import io.renren.modules.app.service.UserUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-12-06
 */
@RestController
@RequestMapping("/api/user/relation")
public class UserUserRelationController extends BaseController {
    @Autowired
    UserUserRelationService relationService;


    @RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<?> getAppQRCode(UserUserRelationPO relationPO) throws Exception {

        QueryWrapper<UserUserRelationPO> queryWrapper=new QueryWrapper();
        queryWrapper.eq("parent_user_id" ,relationPO.getParentUserId());
        queryWrapper.eq("child_user_id" ,relationPO.getChildUserId());
        UserUserRelationPO relationPO1=relationService.getOne(queryWrapper);
        if(relationPO1==null){
            boolean flag=relationService.save(relationPO);
        }
        return Result.success();
    }
}
