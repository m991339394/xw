package io.renren.modules.app.controller.user;


import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.vo.UserVO;
import io.renren.modules.app.service.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-10-05
 */
@RestController
@RequestMapping("/api/user/user")
public class UserUserController extends BaseController {
	@Autowired
	UserUserService userService;

	 /**
     * 
     * @Title: findByOpenId   
     * @Description: 通过openid查找用户   
     * @param: @param openid
     * @param: @return
     * @param: @throws Exception      
     * @return: Result<?>      
     * @date: 2019年10月5日:下午4:27:55
     * @throws
     */
    @RequestMapping(value = "/findByOpenId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> findByOpenId(@RequestParam(value = "openid") String openid) throws Exception {
    	UserVO user = userService.findByOpenId(openid);
    	return Result.success(user);
    }
}
