package io.renren.modules.app.controller.user;

import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.HCardHomeSwiperPO;
import io.renren.modules.app.service.UserHCardHomeSwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 心意卡轮播图 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-10-10
 */
@RestController
@RequestMapping("/api/user/hCardHomeSwiper")
public class UserHCardHomeSwiperController extends BaseController {
	@Autowired
	UserHCardHomeSwiperService swiperService;
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 查询卡类型   
	 * @param: @return      
	 * @return: Result<?>      
	 * @throws
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> list(){
		Map<String, Object> relMap = new HashMap<String, Object>();
    	try {
    		List<HCardHomeSwiperPO> list=swiperService.list();
    		relMap.put("data", list);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
    	return Result.success(relMap);
	}
}
