package io.renren.modules.app.controller.admin;//package io.renren.modules.app.controller.admin;
//
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//
//import io.renren.modules.app.common.Result;
//import io.renren.modules.app.model.po.HCardPO;
//import io.renren.modules.app.model.po.HCardPricePO;
//import io.renren.modules.app.service.AdminHCardService;
//import io.renren.modules.app.utility.BaseController;
//
///**
// * <p>
// * 心意卡 前端控制器
// * </p>
// *
// * @author cth
// * @since 2019-10-12
// */
//@RestController
//@RequestMapping("/api/admin/hCard")
//public class AdminHCardController extends BaseController {
//	@Autowired
//	AdminHCardService adminHCardService;
//	
//	/**
//	 * 
//	 * @Title: list   
//	 * @Description: 查询心意卡   
//	 * @param: @return      
//	 * @return: Result<?>      
//	 * @date: 2019年10月12日:下午7:50:08
//	 * @throws
//	 */
//	@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//	public Result<?> list(){
//    	
//		List<HCardPO> list=adminHCardService.list();
//    	return Result.success(list);
//	}
//	
//	/**
//	 * 
//	 * @Title: getByHCardTypeId   
//	 * @Description: 通过卡类型查询心意卡   
//	 * @param: @param hCardTypeId
//	 * @param: @return      
//	 * @return: Result<?>      
//	 * @date: 2019年10月12日:下午7:50:02
//	 * @throws
//	 */
//	@RequestMapping(value = "/getHCardByHCardTypeId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//	public Result<?> getByHCardTypeId(@RequestParam(value = "hCardTypeId") Long hCardTypeId){
//		
//		QueryWrapper<HCardPO> queryWrapper=new QueryWrapper<HCardPO>();
//		queryWrapper.eq("hCard_type_id", hCardTypeId);
//		List<HCardPO> list=adminHCardService.list(queryWrapper);
//    	return Result.success(list);
//	}
//	
//	/**
//	 * 
//	 * @Title: add   
//	 * @Description: 新增心意卡   
//	 * @param: @param hCardPO
//	 * @param: @return      
//	 * @return: Result<?>      
//	 * @date: 2019年10月12日:下午7:53:00
//	 * @throws
//	 */
//	@RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public Result<?> add(@RequestBody HCardPO hCardPO){
//			
//		boolean flag=adminHCardService.save(hCardPO);
//		return flag?Result.success():Result.fail();
//	}
//	
//	/**
//	 * 
//	 * @Title: update   
//	 * @Description: 修改心意卡   
//	 * @param: @param hCardPO
//	 * @param: @return      
//	 * @return: Result<?>      
//	 * @date: 2019年10月12日:下午7:53:38
//	 * @throws
//	 */
//	@RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	public Result<?> update(@RequestBody HCardPO hCardPO){
//		
//		boolean flag=adminHCardService.updateById(hCardPO);
//		return flag?Result.success():Result.fail();
//	}
//	
//	/**
//	 * 
//	 * @Title: getByHCardTypeId   
//	 * @Description: 删除心意卡   
//	 * @param: @param hCardTypeId
//	 * @param: @return      
//	 * @return: Result<?>      
//	 * @date: 2019年10月12日:下午7:54:35
//	 * @throws
//	 */
//	@RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//	public Result<?> delete(@RequestParam(value = "id") Long id){
//		
//		boolean flag = adminHCardService.removeById(id);
//		return flag?Result.success():Result.fail();
//	}
//	
//
//}
