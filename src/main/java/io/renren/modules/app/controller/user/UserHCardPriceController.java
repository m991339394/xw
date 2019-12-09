package io.renren.modules.app.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.Constants.HCardConstants;
import io.renren.common.Result;
import io.renren.modules.app.model.po.HCardPricePO;
import io.renren.modules.app.service.UserHCardPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 心意卡价格 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
@RestController
@RequestMapping("/api/user/hCardPrice")
public class UserHCardPriceController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserHCardPriceService userHCardPriceService;
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 根据卡类型查询卡面图   
	 * @param: @return      
	 * @return: Result<?>      
	 * @throws
	 */
	@RequestMapping(value = "/getHCardPriceByHCardTypeId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardPriceByHCardTypeId(@RequestParam(value = "hCardTypeId") Long hCardTypeId){
		Map<String, Object> relMap = new HashMap<String, Object>();
    	try {
    		QueryWrapper<HCardPricePO> queryWrapper=new QueryWrapper<HCardPricePO>();
    		queryWrapper.eq("hCard_type_id", hCardTypeId);
    		queryWrapper.eq("upper_shelf", HCardConstants.UPPER_SHELF);
    		List<HCardPricePO> list=userHCardPriceService.list(queryWrapper);
    		relMap.put("data", list);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
    	return Result.success(relMap);
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
	@RequestMapping(value = "/getBuyRecord.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getBuyRecord(@RequestParam(value = "hCardTypeId") Long hCardTypeId){
		Map<String, Object> relMap = new HashMap<String, Object>();
		try {
			QueryWrapper<HCardPricePO> queryWrapper=new QueryWrapper<HCardPricePO>();
			queryWrapper.eq("hCard_type_id", hCardTypeId);
			List<HCardPricePO> list=userHCardPriceService.list(queryWrapper);
			relMap.put("data", list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
		return Result.success(relMap);
	}
	
}
