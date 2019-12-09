package io.renren.modules.app.controller.user;

import io.renren.common.Constants.HCardGiveLogConstants;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.service.UserUserHCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 心意卡转赠记录表 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-10-24
 */
@RestController
@RequestMapping("/api/user/hCardGiveLog")
public class UserHCardGiveLogController extends BaseController {
	@Autowired
	UserUserHCardService userHCardService;
	
	/**
	 * 
	 * @Title: getHCardOrder   
	 * @Description: 心意卡转赠记录
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月4日:下午4:40:46
	 * @throws
	 */
	@RequestMapping(value = "/getGiveRecord.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getGiveRecord(@RequestParam(value = "openid") String openid){
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("openid", openid);
			map.put("state", HCardGiveLogConstants.RECEIVED);
    		List<UserHCardPO> list=userHCardService.getGiveRecord(map);
			return Result.success(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
}
