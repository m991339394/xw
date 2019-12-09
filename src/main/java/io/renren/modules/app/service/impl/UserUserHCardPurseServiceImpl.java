package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.Constants.HCardBalanceLogConstants;
import io.renren.common.Result;
import io.renren.common.exception.RRException;
import io.renren.modules.app.dao.UserUserHCardPurseDao;
import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import io.renren.modules.app.model.po.UserHCardPursePO;
import io.renren.modules.app.model.po.UserHCardPurseRefundLogPO;
import io.renren.modules.app.service.UserUserHCardBalanceLogService;
import io.renren.modules.app.service.UserUserHCardPurseRefundLogService;
import io.renren.modules.app.service.UserUserHCardPurseService;
import io.renren.modules.app.service.thirdParty.wxSdk.WXPayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户心意卡钱包 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@Service
public class UserUserHCardPurseServiceImpl extends ServiceImpl<UserUserHCardPurseDao, UserHCardPursePO> implements UserUserHCardPurseService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserUserHCardPurseDao hCardPurseDao;
	@Autowired
	UserUserHCardBalanceLogService hCardBalanceLogService;
	@Autowired
	UserUserHCardPurseRefundLogService purseRefundLogService;
	
	@Override
	public int recharge(UserHCardPursePO userHCardPursePO) {
		// TODO Auto-generated method stub
		userHCardPursePO.setUpdateTime(new Date());
		return hCardPurseDao.recharge(userHCardPursePO);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> pay(UserHCardBalanceLogPO hCardBalanceLogPO) {
		// TODO Auto-generated method stub
		synchronized (hCardBalanceLogPO.getOrderId()) {
			try {
				QueryWrapper<UserHCardBalanceLogPO> queryWrapper=new QueryWrapper<UserHCardBalanceLogPO>();
				queryWrapper.eq("orderId", hCardBalanceLogPO.getOrderId());
				UserHCardBalanceLogPO hCardBalanceLog=hCardBalanceLogService.getOne(queryWrapper);
				if(hCardBalanceLog!=null) {
					return Result.success("订单已经支付");
				}
				
				UserHCardPursePO purse =hCardPurseDao.selectById(hCardBalanceLogPO.getOpenid());
				Float consumptionAmount=hCardBalanceLogPO.getBalance();
				Float balance=purse.getBalance();
				Float balanceFront=balance;
				Float balanceAfter=balanceFront-consumptionAmount;
				if(consumptionAmount<=balance) {
					purse.setBalance(balance-consumptionAmount);
					purse.setUpdateTime(new Date());
					// 支付
					int num = hCardPurseDao.updateById(purse);
					// 支付成功
					if(num==1) {
						// 增加日志记录
						hCardBalanceLogPO.setBalanceFront(balanceFront);
						hCardBalanceLogPO.setBalanceAfter(balanceAfter);
						hCardBalanceLogPO.setBalance(consumptionAmount);
						hCardBalanceLogPO.setUseStatus(HCardBalanceLogConstants.USE_STATES_CONSUME);
						hCardBalanceLogPO.setSourceType(HCardBalanceLogConstants.SOURCE_TYPE_CONSUME); 
						boolean flag=hCardBalanceLogService.insert(hCardBalanceLogPO);
						if(flag) {
							return Result.success("支付成功");
						}else {
							// 抛出异常
							throw new RRException("增加日志记录" + "异常,"+" OrderId="+hCardBalanceLogPO.getOrderId());
						}
					}else {
						return Result.fail("支付失败");
					}
					
				}else {
					return Result.fail("余额不足");
				}
			} catch (Exception e) {
				throw new RRException("支付失败" + "异常,"+" OrderId="+hCardBalanceLogPO.getOrderId() ,e);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> refund(UserHCardBalanceLogPO hCardBalanceLogPO) {
		// TODO Auto-generated method stub
		String err_code_des="退款失败";
		String OrderId=hCardBalanceLogPO.getOrderId();
		synchronized (OrderId) {
			try {
				UserHCardPursePO purse =hCardPurseDao.selectById(hCardBalanceLogPO.getOpenid());
				Float refundAmount=hCardBalanceLogPO.getBalance();
				Float balanceFront=purse.getBalance();
				Float balanceAfter=balanceFront+refundAmount;
				purse.setBalance(balanceAfter);
				purse.setUpdateTime(new Date());
				
				// 查询退款金额，是否大于订单金额
				Map resultMap=HCardBalanceLogByOrderId(hCardBalanceLogPO);
				if(WXPayConstants.FAIL.equals(resultMap.get("result_code"))) {
					// 抛出异常
					err_code_des="退款失败" + "异常,"+" OrderId="+OrderId+" err_code_des="+resultMap.get("err_code_des");
					return Result.fail(err_code_des);
				}
				
				// 退款
				int num = hCardPurseDao.updateById(purse);
				// 退款成功
				if(num==1) {
					// 增加余额变动记录
					hCardBalanceLogPO.setBalanceFront(balanceFront);
					hCardBalanceLogPO.setBalanceAfter(balanceAfter);
					hCardBalanceLogPO.setBalance(refundAmount);
					hCardBalanceLogPO.setUseStatus(HCardBalanceLogConstants.USE_STATES_REFUND);
					hCardBalanceLogPO.setSourceType(HCardBalanceLogConstants.SOURCE_TYPE_REFUND);
					boolean flag=hCardBalanceLogService.insert(hCardBalanceLogPO);
					if(!flag){
						// 抛出异常
						err_code_des = "增加日志记录" + "异常,"+" OrderId="+OrderId;
						throw new RRException(err_code_des ,new Throwable());
					}
					// 增加退款成功记录
					UserHCardPurseRefundLogPO purseRefundLogPO=new UserHCardPurseRefundLogPO();
					purseRefundLogPO.setOpenid(hCardBalanceLogPO.getOpenid());
					purseRefundLogPO.setOrderId(OrderId);
					purseRefundLogPO.setAmount(refundAmount);
					purseRefundLogPO.setCreateTime(new Date());
					purseRefundLogPO.setState(1);
					flag=purseRefundLogService.save(purseRefundLogPO);
					if(!flag){
						// 抛出异常
						err_code_des = "增加退款记录" + "异常,"+" OrderId="+OrderId;
						throw new RRException(err_code_des ,new Throwable());
					}
						return Result.success("退款成功" ,"退款成功");
				}else {
					// 增加退款失败记录
//					UserHCardPurseRefundLogPO purseRefundLogPO=new UserHCardPurseRefundLogPO();
//					purseRefundLogPO.setOpenid(hCardBalanceLogPO.getOpenid());
//					purseRefundLogPO.setOrderId(OrderId);
//					purseRefundLogPO.setAmount(refundAmount);
//					purseRefundLogPO.setCreateTime(new Date());
//					purseRefundLogPO.setState(0);
//					purseRefundLogService.save(purseRefundLogPO);
//					err_code_des = "退款失败" + "异常,"+" OrderId="+OrderId;
					logger.info(err_code_des);
					return Result.fail("退款失败！");
				}
			} catch (Exception e) {
				throw new RRException(err_code_des ,e);
			}
		}
		
	}
	
	
	public Map HCardBalanceLogByOrderId(UserHCardBalanceLogPO hCardBalanceLogPO){
		QueryWrapper<UserHCardBalanceLogPO> queryWrapper=new QueryWrapper<UserHCardBalanceLogPO>();
		queryWrapper.eq("orderId", hCardBalanceLogPO.getOrderId());
		queryWrapper.orderByAsc("id");
		List<UserHCardBalanceLogPO> balanceLogPOs=hCardBalanceLogService.list(queryWrapper);
		
		Map<String ,String> resultMap=new HashMap<String ,String>();
		
		int OrderAmount =  Math.round(balanceLogPOs.get(0).getBalance()*100);
		Float sum = 0f;
		for (int i = 1; i < balanceLogPOs.size(); i++) {
			sum += balanceLogPOs.get(i).getBalance();
		}
		int refundSumAmount = Math.round(sum*100);
		int refundAmount = Math.round(hCardBalanceLogPO.getBalance()*100);
		
		if(refundAmount+refundSumAmount<=OrderAmount) {
			resultMap.put("result_code", "SUCCESS");
		}else if(refundSumAmount==OrderAmount){
			resultMap.put("result_code", "FAIL");
			resultMap.put("err_code_des", "订单已全额退款");
		}else if(refundAmount+refundSumAmount>OrderAmount) {
			resultMap.put("result_code", "FAIL");
			resultMap.put("err_code_des", "退款金额大于订单金额");
		}
		
		return resultMap;
	}
	
	

}
