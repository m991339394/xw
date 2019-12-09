package io.renren.modules.app.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.Constants.CouponConstants;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.UserCouponPO;
import io.renren.modules.app.service.UserUserCouponService;
import io.renren.common.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户的心意卡优惠券 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-10-19
 */
@RestController
@RequestMapping("/api/user/userCoupon")
public class UserUserCouponController extends BaseController {
	@Autowired
	UserUserCouponService couponService;
	
	/**
     * @Title: findByOpenId   
     * @Description: 查询用户的心意卡优惠券   
     * @param: @param openid
     * @param: @return
     * @param: @throws Exception
     * @return: Result<?>      
     * @date: 2019年10月9日:下午12:27:55
     * @throws
     */
    @RequestMapping(value = "/findByOpenId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> findByOpenId(@RequestParam(value = "openid") String openid) throws Exception {
    	QueryWrapper<UserCouponPO> queryWrapper=new QueryWrapper<UserCouponPO>();
    	queryWrapper.eq("openid", openid);
    	queryWrapper.eq("state", CouponConstants.STATE_NOT_USED);
    	queryWrapper.gt("expire_time", TimeUtil.getTimestamp());
    	List<UserCouponPO> userCouponPOs = couponService.list(queryWrapper);
    	return Result.success(userCouponPOs); 
    }
    
    
    @RequestMapping(value = "/receiveCoupon.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> receiveCoupon(UserCouponPO userCouponPO) throws Exception {
    	
    	userCouponPO.setState(0);
    	boolean flag = couponService.save(userCouponPO);
    	return flag?Result.success():Result.fail(); 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
