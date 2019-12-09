package io.renren.modules.app.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.Constants.HCardConstants;
import io.renren.common.Constants.HCardGiveLogConstants;
import io.renren.common.Result;
import io.renren.modules.app.model.form.ExpressDeliveryForm;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.model.vo.ExpressDeliveryVO;
import io.renren.modules.app.service.UserUserHCardService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
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
 * 用户心意卡 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-10-04
 */
@RestController
@RequestMapping("/api/user/userHCard")
public class UserUserHCardController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserUserHCardService userHCardService;
	
	
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
	@RequestMapping(value = "/getBuyRecord.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getBuyRecord(@RequestParam(value = "openid") String openid){
    	try {
    		List<UserHCardPO> list=userHCardService.getBuyRecord(openid);
    		return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getHCardOrder   
	 * @Description: 我的卡包   
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月4日:下午4:40:46
	 * @throws
	 */
	@RequestMapping(value = "/getMyHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getMyHCard(@RequestParam(value = "openid") String openid){
		try {
			QueryWrapper<UserHCardPO> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("openid" ,openid);
			queryWrapper.orderByDesc("fetch_time");
			List<UserHCardPO> list=userHCardService.list(queryWrapper);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getHCardOrder   
	 * @Description: 赠送心意卡给朋友(将心意卡锁定为转赠中)
	 * @param: @param hCardId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月4日:下午4:40:46
	 * @throws
	 */
	@RequestMapping(value = "/giveHCardToFriend.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> giveHCardToFriend(@RequestParam(value = "hCardId") Long hCardId
										,@RequestParam(value = "openId") String openId
										,@RequestParam(value = "uuid") String uuid){
		try {
			Result result=userHCardService.giveHCardToFriend(hCardId,openId ,uuid);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: acceptFriendToMeHCard   
	 * @Description: 打开好友赠送给我的心意卡   
	 * @param: @param hCardId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月5日:下午3:34:39
	 * @throws
	 */
	@RequestMapping(value = "/lookFriendToMeHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> lookFriendToMeHCard(@RequestParam(value = "hCardId") Long giverHCardId
											,@RequestParam(value = "openid") String myOpenid
											,@RequestParam(value = "uuid") String uuid){
		try {
			Result result=userHCardService.lookFriendToMeHCard(giverHCardId,myOpenid ,uuid);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: acceptFriendToMeHCard   
	 * @Description: 领取好友赠送给我的心意卡   
	 * @param: @param hCardId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月5日:下午3:34:39
	 * @throws
	 */
//	@RequestMapping(value = "/receiveFriendToMeHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//	public Result<?> receiveFriendToMeHCard(@RequestParam(value = "hCardId") Long hCardId
//											,@RequestParam(value = "openid") String myOpenid){
//		try {
//			Result result=userHCardService.receiveFriendToMeHCard(hCardId ,myOpenid);
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Result.fail(500,e.getMessage());
//		}
//	}
	@RequestMapping(value = "/receiveFriendToMeHCard.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> receiveFriendToMeHCard(ExpressDeliveryForm expressDeliveryForm){
		try {
			if(StringUtils.isNotEmpty(expressDeliveryForm.getUuid())){
				Result result=userHCardService.receiveFriendToMeHCard(expressDeliveryForm);
				return result;
			}else{
				Result result=userHCardService.activeHCard(expressDeliveryForm);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}



	
	/**
	 * 
	 * @Title: receiveFriendToMeHCard   
	 * @Description: 撤回赠送给朋友的心意卡   
	 * @param: @param hCardId
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月6日:下午3:49:50
	 * @throws
	 */
	@RequestMapping(value = "/cancelGiveHCardToFriend.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> cancelGiveHCardToFriend(@RequestParam(value = "hCardId") Long hCardId 
											,@RequestParam(value = "openid" ,required = false) String openid){
		try {
			Result result=userHCardService.cancelGiveHCardToFriend(hCardId , HCardConstants.STATE_FAIL_DONATION);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: cancelGiveHCardToFriend   
	 * @Description: 激活心意卡   
	 * @param: @param hCardId
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午4:31:28
	 * @throws
	 */
//	@RequestMapping(value = "/activeHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//	public Result<?> activeHCard(@RequestParam(value = "hCardId") Long hCardId){
//		try {
//			Result result=userHCardService.activeHCard(hCardId);
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Result.fail(500,e.getMessage());
//		}
//	}
	@RequestMapping(value = "/activeHCard.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> activeHCard(ExpressDeliveryForm expressDeliveryForm){
		try {
			Result result=userHCardService.activeHCard(expressDeliveryForm);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: refund   
	 * @Description: 申请退款(测试)   
	 * @param: @param hCardOrderPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月6日:下午6:08:03
	 * @throws
	 */
	@RequestMapping(value = "/refundTest.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> refundTest(@RequestParam(value = "hCardId") Long hCardId
			,@RequestParam(value = "refund_fee") Float refund_fee){
		Result result=userHCardService.refund(hCardId ,refund_fee);
		return result;
	}
	
	/**
	 * 
	 * @Title: refund   
	 * @Description: 申请退款   
	 * @param: @param hCardOrderPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月6日:下午6:08:03
	 * @throws
	 */
	@RequestMapping(value = "/refund.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> refund(@RequestParam(value = "hCardId") Long hCardId){
		Result result=userHCardService.refund(hCardId);
		return result;
	}
	
	/**
	 * 
	 * @Title: getMyHCard   
	 * @Description: 未领取的心意卡 (0 未领取，1 已领取，2 已过期  )
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月16日:下午6:15:13
	 * @throws
	 */
	@RequestMapping(value = "/getHCardByReceive.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardByReceive(@RequestParam(value = "openid") String openid
								,@RequestParam(value = "receive") Integer state){
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("openid", openid);
			map.put("state", state);
			List<UserHCardPO> list=userHCardService.getReceiveRecord(map);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: notReceivedHCard   
	 * @Description: 可使用的心意卡 (0未激活 ,3转赠失败 ，6领取的)   
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月16日:下午6:50:57
	 * @throws
	 */
	@RequestMapping(value = "/getMayUseHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getMayUseHCardByState(@RequestParam(value = "openid") String openid){
		try {
			QueryWrapper<UserHCardPO> queryWrapper=new QueryWrapper<>();
			List<Integer> states=new ArrayList<Integer>();
			states.add(HCardConstants.STATE_NOT_ACTIVE);
			states.add(HCardConstants.STATE_FAIL_DONATION);
			states.add(HCardConstants.STATE_SUCCESS_RECEIVED);
			queryWrapper.eq("openid" ,openid);
			queryWrapper.in("state" ,states);
			queryWrapper.orderByDesc("fetch_time");
			List<UserHCardPO> list=userHCardService.list(queryWrapper);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: notReceivedHCard   
	 * @Description: 转赠中的心意卡 (2转赠中)   
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月16日:下午6:50:57
	 * @throws
	 */
	@RequestMapping(value = "/getInDonationHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getInDonationHCard(@RequestParam(value = "openid") String openid){
		try {
			QueryWrapper<UserHCardPO> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("openid" ,openid);
			queryWrapper.eq("state" ,HCardConstants.STATE_IN_DONATION);
			queryWrapper.orderByDesc("give_time");
			List<UserHCardPO> list=userHCardService.list(queryWrapper);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: notReceivedHCard   
	 * @Description: 可转赠的心意卡 (0未激活 ，3转赠失败 ，6领取的) 
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月16日:下午6:50:57
	 * @throws
	 */
	@RequestMapping(value = "/getMayDonationHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getMayDonationHCard(@RequestParam(value = "openid") String openid){
		try {
			QueryWrapper<UserHCardPO> queryWrapper=new QueryWrapper<>();
			List<Integer> states=new ArrayList<Integer>();
			states.add(HCardConstants.STATE_NOT_ACTIVE);
			states.add(HCardConstants.STATE_FAIL_DONATION);
			states.add(HCardConstants.STATE_SUCCESS_RECEIVED);
			queryWrapper.eq("openid" ,openid);
			queryWrapper.in("state" ,states);
			queryWrapper.gt("gift_restriction_count", 0);
			queryWrapper.orderByDesc("fetch_time");
			List<UserHCardPO> list=userHCardService.list(queryWrapper);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: notReceivedHCard   
	 * @Description: 转赠完的心意卡 (4转赠成功) 
	 * @param: @param openid
	 * @param: @param receive
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月16日:下午6:50:57
	 * @throws
	 */
	@RequestMapping(value = "/getSuccessDonationHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getSuccessDonationHCard(@RequestParam(value = "openid") String openid){
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("openid", openid);
			map.put("state", HCardGiveLogConstants.RECEIVED);
			List<UserHCardPO> list=userHCardService.getGiveRecord(map);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}

	/**
	 * @Author jgl
	 * @Description 已使用礼品卡(快递未到货的)
	 * @Date 15:12 2019/12/4
	 * @Param [openid]
	 * @return io.renren.common.Result<?>
	 **/
	@RequestMapping(value = "/getUsedHCard.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getUsedHCard(@RequestParam(value = "openid") String openid){
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("openid", openid);
			map.put("state", 0);
			List<ExpressDeliveryVO> list=userHCardService.getUsedHCard(map);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: notReceivedHCard   
	 * @Description: 查询心意卡（1 未领取的心意卡  2可使用的心意卡  3可转赠的心意卡  4转赠中的心意卡 5转赠完的心意卡 6已使用的心意卡）
	 * @param: @param openid
	 * @param: @param receive
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月16日:下午6:50:57
	 * @throws
	 */
	@RequestMapping(value = "/getHCards.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getHCards(@RequestParam(value = "openid") String openid
				,@RequestParam(value = "type") Integer type){
		try {
			Result<?> result = new Result<T>();
			switch (type) {
				case 1:
					result = getHCardByReceive(openid,HCardConstants.NOT_RECEIVE);
					break;
				case 2:
					result = getMayUseHCardByState(openid);      
					break;
				case 3:
					result = getMayDonationHCard(openid);
					break;
				case 4:
					result = getInDonationHCard(openid);
					break;
				case 5:
					result = getSuccessDonationHCard(openid);
					break;
				case 6:
					result = getUsedHCard(openid);
					break;
				default:
					break;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
	}
	
}
