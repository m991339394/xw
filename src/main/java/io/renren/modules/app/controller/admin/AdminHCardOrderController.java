package io.renren.modules.app.controller.admin;


import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.HCardOrderPO;
import io.renren.modules.app.service.AdminHCardOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * 用户购买心意卡订单记录 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/admin/hCardOrder")
public class AdminHCardOrderController extends BaseController {
	@Autowired
	AdminHCardOrderService adminHCardOrderService;
	
	/**
	 * 
	 * @Title: getHCardPriceByHCardTypeId
	 * @Description: 订单记录   
	 * @param: @param hCardhOrderPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年9月28日:下午5:18:13
	 * @throws
	 */
	@RequestMapping(value = "/getHCardOrder.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardOrder(@RequestParam(value = "hCardTypeId") Long hCardTypeId){
    	try {
    		List<HCardOrderPO> list=adminHCardOrderService.list();
    		return Result.success(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
}
