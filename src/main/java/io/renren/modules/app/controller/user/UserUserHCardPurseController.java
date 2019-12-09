package io.renren.modules.app.controller.user;

import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import io.renren.modules.app.model.po.UserHCardPursePO;
import io.renren.modules.app.service.UserUserHCardPurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户心意卡钱包 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/api/user/userHCardPurse")
public class UserUserHCardPurseController extends BaseController {
	@Autowired
	UserUserHCardPurseService hCardPurseService;
	
	/**
	 * 
	 * @Title: getUserHCardPurse   
	 * @Description: 心意卡余额
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月14日:下午2:44:42
	 * @throws
	 */
	@RequestMapping(value = "/getUserHCardPurse.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getUserHCardPurse(@RequestParam(value = "openid") String openid){
    	try {
    		UserHCardPursePO userHCardPursePO=hCardPurseService.getById(openid);
    		return Result.success(userHCardPursePO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: pay   
	 * @Description: 用心意卡余额支付   
	 * @param: @param hCardConsumeRecordPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月14日:下午4:34:59
	 * @throws
	 */
	@RequestMapping(value = "/pay.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> pay(UserHCardBalanceLogPO hCardBalanceLogPO){
		try {
			Result<?> result =hCardPurseService.pay(hCardBalanceLogPO);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: refund   
	 * @Description: 退款（心意卡余额）
	 * @param: @param hCardBalanceLogPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月14日:下午9:18:47
	 * @throws
	 */
	@RequestMapping(value = "/refund.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> refund(UserHCardBalanceLogPO hCardBalanceLogPO){
		try {
			Result result =hCardPurseService.refund(hCardBalanceLogPO);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	
}
