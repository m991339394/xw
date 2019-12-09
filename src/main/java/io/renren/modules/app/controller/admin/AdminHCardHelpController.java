package io.renren.modules.app.controller.admin;


import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.HCardHelpPO;
import io.renren.modules.app.service.AdminHCardHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 帮助中心
 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-18
 */
@RestController
@RequestMapping("/api/admin/hCardHelp")
public class AdminHCardHelpController extends BaseController {
	@Autowired
	AdminHCardHelpService helpService;
	
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 查询  
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月12日:下午7:50:08
	 * @throws
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> list(){
    	
		List<HCardHelpPO> list=helpService.list();
    	return Result.success(list);
	}
	
	
	/**
	 * 
	 * @Title: add   
	 * @Description: 新增 
	 * @param: @param hCardTypePO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:25:57
	 * @throws
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> add(@RequestBody HCardHelpPO hCardHelpPO){
		
		boolean flag=helpService.save(hCardHelpPO);
		return flag?Result.success():Result.fail("新增失败！");
	}
	
	/**
	 * 
	 * @Title: add   
	 * @Description: 修改
	 * @param: @param hCardTypePO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:25:57
	 * @throws
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> update(@RequestBody HCardHelpPO hCardHelpPO){
		
		boolean flag=helpService.updateById(hCardHelpPO);
		return flag?Result.success():Result.fail("修改失败！");
	}
	
	/**
	 * 
	 * @Title: delete   
	 * @Description: 删除   
	 * @param: @param id
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:29:17
	 * @throws
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> delete(@RequestParam(value = "id") Long id){
		
		boolean flag=helpService.removeById(id);
		return flag?Result.success():Result.fail("删除失败！");
	}
}
