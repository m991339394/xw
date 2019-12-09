package io.renren.modules.app.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.LayerMsg;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.form.UserHCardForm;
import io.renren.modules.app.model.vo.UserHCardVO;
import io.renren.modules.app.service.AdminUserHCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户心意卡 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/admin/userHCard")
public class AdminUserHCardController extends BaseController {
	@Autowired
	AdminUserHCardService adminUserHCardService;
	
	/**
	 * 
	 * @Title: getHCardOrder   
	 * @Description: 心意卡购买记录
	 * @param: @param request
	 * @param: @param hCardOrderPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月4日:下午4:08:02
	 * @throws
	 */
	@RequestMapping(value = "/getBuyRecord.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg getBuyRecord(@RequestBody UserHCardForm userHCardForm){
    	try {
    		
    		int pageNo=userHCardForm.getPageNo();
			int pageSize=userHCardForm.getPageSize();
			PageHelper.startPage(pageNo, pageSize);

    		List<UserHCardVO> list=adminUserHCardService.getBuyRecord(userHCardForm);
    		PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}

	/**
	 *
	 * @Title: getConsumeRecord
	 * @Description: 消费记录
	 * @param: @param userHCardForm
	 * @param: @return
	 * @return: LayerMsg
	 * @date: 2019年11月29日:上午10:12:28
	 * @throws
	 */
	@RequestMapping(value = "/getConsumeRecord.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg getConsumeRecord(UserHCardForm userHCardForm){

		try {
			int pageNo=userHCardForm.getPageNo();
			int pageSize=userHCardForm.getPageSize();
			PageHelper.startPage(pageNo, pageSize);

			List<UserHCardVO> list=adminUserHCardService.getConsumeRecord(userHCardForm);
			PageInfo pageInfo = new PageInfo(list, pageSize);
			return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
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
	@RequestMapping(value = "/getGiveRecord.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg getGiveRecord(@RequestBody UserHCardForm userHCardForm){
		try {
			int pageNo=userHCardForm.getPageNo();
			int pageSize=userHCardForm.getPageSize();
			PageHelper.startPage(pageNo, pageSize);

			List<UserHCardVO> list=adminUserHCardService.getGiveRecord(userHCardForm);
			PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
	/**
	 * 
	 * @Title: getHCardOrder 
	 * @Description: 激活记录   
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月4日:下午4:40:46
	 * @throws
	 */
	@RequestMapping(value = "/getActivedHCard.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg getActivedHCard(@RequestBody UserHCardForm userHCardForm){
		
		try {
			int pageNo=userHCardForm.getPageNo();
			int pageSize=userHCardForm.getPageSize();
			PageHelper.startPage(pageNo, pageSize);

			List<UserHCardVO> list=adminUserHCardService.getActivedHCard(userHCardForm);
			PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}

	/**
	 *
	 * @Title: getHCards
	 * @Description: 交易记录（1 、购卡记录  2、消费记录  3、转赠记录  4、激活记录）
	 * @param: @param userHCardForm
	 * @param: @return
	 * @return: Result<?>
	 * @date: 2019年11月28日:下午6:20:55
	 * @throws
	 */
	@RequestMapping(value = "/getUserHCardRecord.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg getHCards(@RequestBody UserHCardForm userHCardForm){
		try {
			int type=userHCardForm.getType();
			LayerMsg result = new LayerMsg();
			switch (type) {
				case 1:
					result = getBuyRecord(userHCardForm);
					break;
				case 2:
					result = getConsumeRecord(userHCardForm);
					break;
				case 3:
					result = getGiveRecord(userHCardForm);
					break;
				case 4:
					result = getActivedHCard(userHCardForm);
					break;

				default:
					break;
			}

			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return LayerMsg.fail(e.getMessage());
		}
	}

}
