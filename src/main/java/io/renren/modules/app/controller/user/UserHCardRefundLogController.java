package io.renren.modules.app.controller.user;


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
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/api/user/hCardRefundLog")
public class UserHCardRefundLogController extends BaseController {
	@Autowired
	UserUserHCardService userHCardService;
	
	/**
	 * 
	 * @Title: notReceivedHCard   
	 * @Description: 心意卡退卡记录
	 * @param: @param openid
	 * @param: @param receive
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月16日:下午6:50:57
	 * @throws
	 */
	@RequestMapping(value = "/getRefundLog.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getRefundLog(@RequestParam(value = "openid") String openid){
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("openid", openid);
			map.put("state", 1);
			List<UserHCardPO> list=userHCardService.getRefundRecord(map);
			return Result.success(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
}
