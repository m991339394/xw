package io.renren.modules.app.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.LayerMsg;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.common.utils.StringUtils;
import io.renren.modules.app.model.po.HCardPricePO;
import io.renren.modules.app.service.AdminHCardPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 心意卡价格 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/admin/hCardPrice")
public class AdminHCardPriceController extends BaseController {
	@Autowired
	AdminHCardPriceService adminHCardPriceService;
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 根据卡类型查询卡价格   
	 * @param: @return      
	 * @return: Result<?>      
	 * @throws
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public LayerMsg list(@RequestParam(value = "pageNo" ,defaultValue = "1") Integer pageNo
						, @RequestParam(value = "pageSize" ,defaultValue = "10") Integer pageSize
						, @RequestParam(value = "hCardTypeId" ,required = false) String hCardTypeId
						, @RequestParam(value = "name" ,required = false) String name){
    	try {
    		PageHelper.startPage(pageNo, pageSize);
    		QueryWrapper<HCardPricePO> queryWrapper=new QueryWrapper<HCardPricePO>();
    		if(StringUtils.isNotEmpty(hCardTypeId)) {
    			queryWrapper.eq("hCard_type_id", hCardTypeId);
    		}
    		if(StringUtils.isNotEmpty(name)) {
    			queryWrapper.likeRight("name", name);
    		}
    		List<HCardPricePO> list=adminHCardPriceService.list(queryWrapper);
    		PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
	/**
	 * 
	 * @Title: add
	 * @Description: 新增卡价格   
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午4:26:18
	 * @throws
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> add(@RequestBody HCardPricePO hCardPricePO){
			
		boolean flag=adminHCardPriceService.save(hCardPricePO);
		return flag?Result.success():Result.fail();
	}
	
	/**
	 * 
	 * @Title: update
	 * @Description: 修改卡价格   
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午4:26:18
	 * @throws
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> update(@RequestBody HCardPricePO hCardPricePO){
		
		hCardPricePO.setUpdatedTime(new Date());
		boolean flag=adminHCardPriceService.updateById(hCardPricePO);
		return flag?Result.success():Result.fail();
	}
	
	/**
	 * 
	 * @Title: update
	 * @Description: 删除卡价格   
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午4:26:18
	 * @throws
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> delete(@RequestParam(value = "id") Long id){
		
		boolean flag=adminHCardPriceService.removeById(id);
		return flag?Result.success():Result.fail();
	}
	
	/**
	 * 
	 * @Title: getSaleRecord   
	 * @Description: 销售记录   
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午4:57:21
	 * @throws
	 */
	@RequestMapping(value = "/getSaleRecord.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public LayerMsg getSaleRecord(@RequestParam(value = "hCardTypeId") Long hCardTypeId
									,@RequestParam(value = "pageNo") int pageNo
									,@RequestParam(value = "pageSize") int pageSize){
		try {
			PageHelper.startPage(pageNo, pageSize);
			QueryWrapper<HCardPricePO> queryWrapper=new QueryWrapper<HCardPricePO>();
			queryWrapper.eq("hCard_type_id", hCardTypeId);
			List<HCardPricePO> list=adminHCardPriceService.list(queryWrapper);
			
			PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
}
