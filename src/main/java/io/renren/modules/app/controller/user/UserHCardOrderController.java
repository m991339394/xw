package io.renren.modules.app.controller.user;

import io.renren.common.Result;
import io.renren.modules.app.model.form.HCardOrderForm;
import io.renren.modules.app.service.UserHCardOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户购买心意卡订单记录 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
@RestController
@RequestMapping("/api/user/hCardOrder")
public class UserHCardOrderController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserHCardOrderService userHCardOrderService;

	/**
	 * 
	 * @Title: getHCardOrder   
	 * @Description: 创建订单   
	 * @param: @param request
	 * @param: @param hCardOrderPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月14日:上午9:53:51
	 * @throws
	 */
	@RequestMapping(value = "/getHCardOrder.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardOrder(HttpServletRequest request, HCardOrderForm hCardOrderForm) {

		Result<?> result = userHCardOrderService.getHCardOrder(request, hCardOrderForm);
		return result;
	}

	/**
	 * 
	 * @Title: successfulPaymentNotify   
	 * @Description: 小程序订单支付成功后的通知
	 * @param: @param out_trade_no
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月14日:上午9:53:37
	 * @throws
	 */
	@RequestMapping(value = "/successfulPaymentNotify.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> successfulPaymentNotify(@RequestParam(value = "out_trade_no") String out_trade_no) {
		try {
			Result result = userHCardOrderService.successfulPaymentNotify(out_trade_no);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500, e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: cancelHCardOrder   
	 * @Description: 关闭订单   
	 * @param: @param hCardOrderPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月14日:上午9:46:34
	 * @throws
	 */
	@RequestMapping(value = "/cancelHCardOrder.do", method = RequestMethod.GET,produces = "application/json;charset=UTF-8") 
	public Result<?> cancelHCardOrder(@RequestParam(value = "id") Long id){ 
		Result result=userHCardOrderService.closeOrder(id); 
		return result;
	}
	 

	/**
	 * 
	 * @Title: refund 
	 * @Description: 直接退款（不需要申请） 
	 * @param: @param hCardOrderPO 
	 * @param: @return 
	 * @return: Result<?> 
	 * @date:2019年10月6日:下午6:08:03 
	 * @throws
	 */
	@RequestMapping(value = "/refundTest.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> refundTest(@RequestParam(value = "out_trade_no") String out_trade_no) {
		Result result = userHCardOrderService.refund(out_trade_no);
		return result;
	}
}
