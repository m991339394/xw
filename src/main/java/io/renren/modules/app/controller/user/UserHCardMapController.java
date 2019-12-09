package io.renren.modules.app.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.Constants.HCardConstants;
import io.renren.common.Result;
import io.renren.modules.app.model.po.HCardMapPO;
import io.renren.modules.app.model.po.HCardPricePO;
import io.renren.modules.app.service.UserHCardMapService;
import io.renren.modules.app.service.UserHCardPriceService;
import io.renren.modules.app.service.UserUserHCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 心意卡卡面图 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-09-27
 */
@RestController
@RequestMapping("/api/user/hCardMap")
public class UserHCardMapController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserHCardMapService userHCardMapService;
	@Autowired
	UserHCardPriceService userHCardPriceService;
	@Autowired
	UserUserHCardService userHCardService;
	
	
	/**
	 * 
	 * @Title: getHCardMapByHCardTypeId   
	 * @Description: 根据卡类型查询卡面图
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年9月28日:下午5:11:41
	 * @throws
	 */
	@RequestMapping(value = "/getHCardMapByHCardTypeId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardMapByHCardTypeId(@RequestParam(value = "hCardTypeId") Long hCardTypeId){
		Map<String, Object> relMap = new HashMap<String, Object>();
    	try {
    		QueryWrapper queryWrapper=new QueryWrapper();
    		queryWrapper.eq("hCard_type_id", hCardTypeId);
    		List<HCardMapPO> hCardMapPOs=userHCardMapService.list(queryWrapper);
    		relMap.put("data", hCardMapPOs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
    	return Result.success(relMap);
	}
	
	
	/**
	 * 
	 * @Title: getHCardByHCardTypeId   
	 * @Description: 根据卡类型查询卡面图和价格
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年9月28日:下午5:12:41
	 * @throws
	 */
	@RequestMapping(value = "/getHCardByHCardTypeId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardByHCardTypeId(@RequestParam(value = "hCardTypeId") Long hCardTypeId
											,@RequestParam(value = "openid") String openid){
		Map<String, Object> relMap = new HashMap<String, Object>();
    	try {
    		QueryWrapper queryWrapper=new QueryWrapper();
    		queryWrapper.eq("hCard_type_id", hCardTypeId);
    		List<HCardMapPO> hCardMapPOs=userHCardMapService.list(queryWrapper);
    		queryWrapper.eq("upper_shelf", HCardConstants.UPPER_SHELF);
    		queryWrapper.orderByDesc("created_time");
    		List<HCardPricePO> hCardPricePOs=userHCardPriceService.list(queryWrapper);
    		// 用户购买记录
    		List<Long> ids=new ArrayList<Long>();
    		for (HCardPricePO hCardPricePO : hCardPricePOs) {
    			ids.add(hCardPricePO.getId());
    		}
//    		Map<String ,Object> buyedMap=new HashMap<String ,Object>();
//    		buyedMap.put("openid", openid);
//    		buyedMap.put("priceIds", ids);
//    		List<UserHCardDTO> userHCardDTOs=userHCardService.getBuyedCount(buyedMap);
//
//    		List<HCardPriceVO> hCardPriceVOs=new ArrayList<HCardPriceVO>();
//    		DozerBeanMapper dozerBeanMapper=new DozerBeanMapper();
//    		for (int i = 0; i < hCardPricePOs.size(); i++) {
//    			HCardPriceVO hCardPriceVO=dozerBeanMapper.map(hCardPricePOs.get(i), HCardPriceVO.class);
//    			for (int j = 0; j < userHCardDTOs.size(); j++) {
//					if(hCardPricePOs.get(i).getId()==userHCardDTOs.get(j).gethCardPriceId()) {
//						hCardPriceVO.setBuyedCount(userHCardDTOs.get(j).getCount());
//						userHCardDTOs.remove(j);
//						break;
//					}
//				}
//    			hCardPriceVOs.add(hCardPriceVO);
//			}
    		
    		relMap.put("hCardMapPOs", hCardMapPOs);
//    		relMap.put("hCardPricePOs", hCardPriceVOs);
    		relMap.put("hCardPricePOs", hCardPricePOs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
    	return Result.success(relMap);
	}
	
}
