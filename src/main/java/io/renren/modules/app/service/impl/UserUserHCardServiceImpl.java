package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.Constants.HCardBalanceLogConstants;
import io.renren.common.Constants.HCardConstants;
import io.renren.common.Constants.HCardGiveLogConstants;
import io.renren.common.Result;
import io.renren.common.exception.RRException;
import io.renren.modules.app.dao.UserUserHCardDao;
import io.renren.modules.app.model.dto.UserHCardDTO;
import io.renren.modules.app.model.form.ExpressDeliveryForm;
import io.renren.modules.app.model.po.*;
import io.renren.modules.app.model.vo.ExpressDeliveryVO;
import io.renren.modules.app.service.*;
import io.renren.modules.app.service.thirdParty.wxSdk.WXOrder;
import io.renren.modules.app.service.thirdParty.wxSdk.WXPayConstants;
import io.renren.modules.job.service.ScheduleJobService;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户心意卡 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-10-04
 */
@Service
public class UserUserHCardServiceImpl extends ServiceImpl<UserUserHCardDao, UserHCardPO> implements UserUserHCardService {
	@Autowired
	UserUserHCardDao userHCardDao ;
	@Autowired
	ScheduleJobService scheduleJobService;
	@Autowired
	WXOrder wXOrder;
	@Autowired
	UserHCardPriceService priceService;
	@Autowired
	UserHCardOrderService hCardOrderService;
	@Autowired
	UserHCardGiveLogService giveLogService;
	@Autowired
	UserUserHCardPurseService hCardPurseService;
	@Autowired
	UserUserHCardBalanceLogService hCardBalanceLogService;
	@Autowired
	UserHCardRefundLogService refundLogService;
	@Autowired
	UserExpressDeliveryService expressDeliveryService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int updateHCardStateById(Long hCardId, Integer state) {
		UserHCardPO userHCardPO=new UserHCardPO();
		userHCardPO.setId(hCardId);
		userHCardPO.setState(state);
		userHCardPO.setGiveTime(new Date());
		return userHCardDao.updateById(userHCardPO);
	}
	
	public int updateHCardStateById(Long hCardId, Integer state ,String uuid ,Date date) {
		UserHCardPO userHCardPO=new UserHCardPO();
		userHCardPO.setId(hCardId);
		userHCardPO.setState(state);
		userHCardPO.setGiveTime(date);
		userHCardPO.sethCardGiveTd(uuid);;
		return userHCardDao.updateById(userHCardPO);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> giveHCardToFriend(Long hCardId, String openid , String uuid) {
		// TODO Auto-generated method stub
		// 锁定心意卡
		String error_msg="";
		int num;
		synchronized (hCardId) {
			
			UserHCardPO userHCardPO=userHCardDao.selectById(hCardId);
			
			if(!userHCardPO.getOpenid().equals(openid)){
				return Result.fail("无权转赠");
			}
			
			HCardGiveLogPO hCardGiveLog=giveLogService.getById(uuid);
			if(hCardGiveLog!=null){
				return Result.fail("uuid已存在");
			}
			
			// 1、判定转赠次数是否大于0
			if(userHCardPO.getGiftRestrictionCount()==0){
				return Result.fail("转赠达到上限");
			}
			// 2、 锁定心意卡
			if(HCardConstants.STATE_IN_DONATION.equals(userHCardPO.getState())){
				return Result.success("现在可以把卡转赠给朋友了");
			}
			
			if(HCardConstants.STATE_NOT_ACTIVE.equals(userHCardPO.getState()) && HCardConstants.STATE_FAIL_DONATION.equals(userHCardPO.getState()) &&
					HCardConstants.STATE_SUCCESS_RECEIVED.equals(userHCardPO.getState())){
				return Result.success("此卡已使用");
			}
			try {
//				锁定心意卡
				Date date=new Date();
				num=updateHCardStateById(hCardId,HCardConstants.STATE_IN_DONATION ,uuid ,date);

				if(num==1) {
					// 2、添加转赠记录
					HCardGiveLogPO hCardGiveLogPO=new HCardGiveLogPO();
					hCardGiveLogPO.setUuid(uuid);
					hCardGiveLogPO.setGiveOpenid(userHCardPO.getOpenid());
					hCardGiveLogPO.setHcardId(userHCardPO.getId());
					hCardGiveLogPO.setGiveTime(date);
					hCardGiveLogPO.setState(HCardGiveLogConstants.NOT_RECEIVE);
					boolean flag=giveLogService.save(hCardGiveLogPO);
					if(!flag){
						error_msg = "添加转赠礼品卡日志记录" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}
				}


				// 3、创建定时任务
//				if(num==1){
//					Date date=new Date();
//					ScheduleJobEntity scheduleJob=new ScheduleJobEntity();
//					String param= ScheduleJobConstants.RETURN_HCARD_PARAMS+ScheduleJobConstants.SEPARATOR+hCardId;
//					String cronExpression = CronUtil.getCronByOnce(ScheduleJobConstants.RETURN_HCARD_DELAY_SECONDS+date.getTime());
//					scheduleJob.setBeanName(ScheduleJobConstants.RETURN_HCARD_BEAN_NAME);
//					scheduleJob.setParams(param);
//					scheduleJob.setCronExpression(cronExpression);
//					scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
//					scheduleJob.setRemark(ScheduleJobConstants.RETURN_HCARD_REMARK);
//					scheduleJob.setCreateTime(date);
////				ValidatorUtils.validateEntity(scheduleJob);
//					boolean flag=scheduleJobService.saveJob(scheduleJob);
//					if(!flag){
//						error_msg="创建定时任务" + "异常,"+" hCardId ="+hCardId;
//						throw new RRException(error_msg ,new Throwable());
//					}
//					scheduleJob.setParams(scheduleJob.getParams()+ScheduleJobConstants.SEPARATOR+scheduleJob.getJobId()+ScheduleJobConstants.SEPARATOR+uuid);
//					flag = scheduleJobService.update(scheduleJob);
//					if(!flag){
//						error_msg="修改定时任务" + "异常,"+" scheduleId ="+scheduleJob.getJobId();
//						throw new RRException(error_msg ,new Throwable());
//					}
//					// 保存任务id到心意卡中
//					UserHCardPO hCardPO=new UserHCardPO();
//					hCardPO.setId(hCardId);
//					hCardPO.setScheduleId(scheduleJob.getJobId());
//					num = userHCardDao.updateById(hCardPO);
//					if(num==0){
//						error_msg="保存任务id到心意卡中" + "异常,"+" hCardId ="+hCardId;
//						throw new RRException(error_msg ,new Throwable());
//					}
//					Map<String, Object> map=new HashMap<String, Object>();
//					// 5、发送可以转卡的通知
//					return flag?Result.success(map,"现在可以把卡转赠给朋友了"):Result.fail("现在还不可以把卡转赠给朋友呢");
//
//				}else {
//					error_msg="锁定心意卡" + "异常,"+" hCardId ="+hCardId;
//					throw new RRException(error_msg ,new Throwable());
//				}

				return num>0?Result.success(0,"现在可以把卡转赠给朋友了"):Result.fail("现在还不可以把卡转赠给朋友呢");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RRException(error_msg ,e);
			}
			
		}


	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> lookFriendToMeHCard(Long hCardId ,String receiveOpenid ,String uuid) {
		// 1、查询心意卡状态 （1）朋友是否已经撤回了红包（2）心意卡红包已经过期
		
		String error_msg="查看红包失败";
		synchronized (hCardId) {
			UserHCardPO hCardPO=userHCardDao.selectById(hCardId);
			HCardGiveLogPO hCardGiveLogPO=giveLogService.getById(uuid);
			Integer state=hCardPO.getState();
			Integer hCardGiveLogState=hCardGiveLogPO.getState();
			
			// 1、转赠者 查看红包
			if(hCardPO.getOpenid().contentEquals(receiveOpenid)) {
				if(state==HCardConstants.STATE_IN_DONATION) { //转赠中
					return Result.success(5,"转赠中");
					
				}else if(state==HCardConstants.STATE_SUCCESS_DONATION) {
					
					return Result.success(5,"已领取");
					
				}else if(state==HCardConstants.STATE_FAIL_DONATION) {
					
					return Result.success(5,"已过期！");
				}
			}
			
				
			if(state==HCardConstants.STATE_SUCCESS_DONATION) {
				
				return Result.success(0,"已领取");
				
			}else if(state==HCardConstants.STATE_FAIL_DONATION) {
				
				return Result.success(0,"已过期！");
			}
			
			if(!hCardPO.gethCardGiveTd().equals(uuid)) {
				return Result.fail("已过期!");
			}

			// 2、添加转赠记录
			Date date=new Date();
			hCardGiveLogPO.setReceiveOpenid(receiveOpenid);
			hCardGiveLogPO.setReceiveTime(date);
			boolean flag=giveLogService.updateById(hCardGiveLogPO);
			if(!flag){
				error_msg = "修改礼品卡转赠记录" + "异常,"+" hCardId ="+hCardId;
				throw new RRException(error_msg ,new Throwable());
			}

			// 3、接受者查看红包
			if(HCardGiveLogConstants.NOT_RECEIVE.equals(hCardGiveLogState)) { //转赠中
				UserHCardPO hCardPO2=new UserHCardPO();
				hCardPO2.setId(hCardPO.getId());
				hCardPO2.setReceiveOpenid(receiveOpenid);
				int num=userHCardDao.updateById(hCardPO2);
				if(num==0){
					error_msg = "添加接受人" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}
				return Result.success("成功的打开红包","领取");
			}else if(HCardGiveLogConstants.RECEIVED.equals(hCardGiveLogState)) {
				
				return Result.fail("已领取");
				
			}else if(HCardConstants.RETURN_DONATION.equals(hCardGiveLogState)) {
				
				return Result.fail("已过期！");
			}
		}
		return Result.fail("此卡已经不存在");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> receiveFriendToMeHCard(Long hCardId, String openid) {
		// TODO Auto-generated method stub
		// 1、查询心意卡状态 （1）心意卡红包是否已经领过了（2）朋友是否已经撤回了红包（3）心意卡红包已经过期
		
		String error_msg="打开红包失败";
		synchronized (hCardId) {
			UserHCardPO friendHCardPO=userHCardDao.selectById(hCardId);
			int state=friendHCardPO.getState();
			if(StringUtils.isEmpty(openid)) {
				return Result.fail("openid为空错误");
			}
			
			if(state==HCardConstants.STATE_SUCCESS_DONATION) { // 已领取
				return Result.fail("已领取");
			}

			if(state==HCardConstants.STATE_ACTIVE || state==HCardConstants.STATE_FAIL_DONATION) { // 已领取
				return Result.fail("已过期");
			}

			if(state==HCardConstants.STATE_IN_DONATION) {
				
				try {
					// 2、修改转赠记录（转赠成功）
					Date date=new Date();
					int num = giveLogService.updateGiveLogByHCardId(friendHCardPO.gethCardGiveTd(),HCardGiveLogConstants.RECEIVED ,date);
					if(num==0){
						// 抛出异常
						error_msg="修改转赠记录" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}

					// 3、将心意卡添加我的卡包中
					UserHCardPO userHCardPO=friendHCardPO;
					userHCardPO.setReceiveOpenid(openid);
					userHCardPO.setOpenid(openid);
					userHCardPO.setGiftRestrictionCount(friendHCardPO.getGiftRestrictionCount()-1);
					userHCardPO.setChannel(HCardConstants.CHANNEL_FROM_FRIENDS);
					userHCardPO.setState(HCardConstants.STATE_SUCCESS_RECEIVED);
					num=userHCardDao.updateById(userHCardPO);
					if(num==0){
						// 抛出异常
						error_msg="将心意卡添加我的卡包中" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}
					
					// 4、关闭退卡的定时任务
					num=pauseJob(friendHCardPO.getScheduleId());
					if(num==0){
						// 抛出异常
						error_msg="关闭退卡的定时任务" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}
					
					return Result.success("成功领取红包","已领取");
				} catch (Exception e) {
					// TODO: handle exception
					throw new RRException(error_msg,e);
				}
				
			}else {
				return Result.fail("红包已过期");
			}
		}
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> receiveFriendToMeHCard(ExpressDeliveryForm expressDeliveryForm){

		// 1、查询心意卡状态 （1）朋友是否已经撤回了红包（2）心意卡红包已经过期

		String error_msg="领取失败";
		try {
			Long hCardId = expressDeliveryForm.getHCardId();
			String receiveOpenid = expressDeliveryForm.getOpenid();
			String uuid = expressDeliveryForm.getUuid();
			synchronized (hCardId) {
			UserHCardPO friendHCardPO=userHCardDao.selectById(hCardId);
			Integer state=friendHCardPO.getState();

			// 1.1 转赠者 查看红包
			if(friendHCardPO.getOpenid().contentEquals(receiveOpenid)) {
				if(HCardConstants.STATE_IN_DONATION.equals(state)) { //转赠中
					return Result.success(5,"转赠中");

				}else if(HCardConstants.STATE_SUCCESS_DONATION.equals(state)) {

					return Result.success(5,"已领取");

				}else if(HCardConstants.STATE_FAIL_DONATION.equals(state)) {

					return Result.success(5,"已退回！");
				}
			}

			// 2 转赠者 查看红包
			if(HCardConstants.STATE_SUCCESS_DONATION.equals(state)) {

				return Result.success(0,"已领取");

			}else if(HCardConstants.STATE_FAIL_DONATION.equals(state)) {

				return Result.success(0,"已过期！");
			}

			if(!friendHCardPO.gethCardGiveTd().equals(uuid)) {
				return Result.fail("已过期!");
			}

			if(HCardConstants.STATE_IN_DONATION.equals(state)) { //转赠中
				// 3、礼品卡转到接受者名下
				Date date = new Date();
				UserHCardPO hCardPO=new UserHCardPO();
				hCardPO.setId(friendHCardPO.getId());
				hCardPO.setOpenid(receiveOpenid);
				hCardPO.setGiftRestrictionCount(friendHCardPO.getGiftRestrictionCount()-1);
				hCardPO.setChannel(HCardConstants.CHANNEL_FROM_FRIENDS);
				hCardPO.setState(HCardConstants.STATE_ACTIVE);
				int num=userHCardDao.updateById(hCardPO);
				if(num==0){
					error_msg = "修改拥有人" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}

				// 4、修改转赠记录
				HCardGiveLogPO hCardGiveLogPO=new HCardGiveLogPO();
				hCardGiveLogPO.setUuid(uuid);
				hCardGiveLogPO.setReceiveTime(date);
				hCardGiveLogPO.setState(HCardGiveLogConstants.RECEIVED);
				boolean flag=giveLogService.updateById(hCardGiveLogPO);
				if(!flag){
					error_msg = "修改转赠卡日志记录" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}

				// 5、添加快递信息
				DozerBeanMapper dozerBeanMapper=new DozerBeanMapper();
				ExpressDeliveryPO expressDeliveryPO=dozerBeanMapper.map(expressDeliveryForm ,ExpressDeliveryPO.class);
				expressDeliveryPO.setUserHcardId(expressDeliveryForm.getHCardId());
				flag=expressDeliveryService.save(expressDeliveryPO);
				if(!flag){
					error_msg="填写快递信息"+"异常"+"hCardId="+hCardId;
					return  Result.fail(error_msg);
				}
				return Result.success("成功的打开红包","领取");
				}else{
					return Result.fail("已过期！");
				}
			}
		}catch (Exception e){
			throw new RRException(error_msg,e);
		}
	}

	@Override
	public Result<?> cancelGiveHCardToFriend(Long hCardId ,Integer state) {
		// TODO Auto-generated method stub
		// 1、改变心意卡状态
		synchronized (hCardId) {
			UserHCardPO userHCard = userHCardDao.selectById(hCardId);
			if(HCardConstants.STATE_IN_DONATION.equals(userHCard.getState())) {
				int num=updateHCardStateById(hCardId ,state);
				Date date=new Date();
				// 2、修改转赠记录(转赠退回)
				if(num==1) {
					HCardGiveLogPO hCardGiveLogPO=giveLogService.getById(userHCard.gethCardGiveTd());
					if(hCardGiveLogPO!=null) {
						num=giveLogService.updateGiveLogByHCardId(userHCard.gethCardGiveTd(),HCardGiveLogConstants.RETURN_DONATION ,date);
						if(num==0){
							// 抛出异常
							throw new RRException("修改转赠记录" + "异常,"+" hCardId ="+hCardId ,new Throwable());
						}
					}
					
				}
				
				// 3、关闭定时任务
//				if(num==1) {
//					num=pauseJob(userHCard.getScheduleId());
//					if(num==0){
//						// 抛出异常
//						throw new RRException("关闭定时任务" + "异常,"+" hCardId ="+hCardId ,new Throwable());
//					}
//				}
				return num==1?Result.success("成功撤回，赠送给朋友的心意卡"):Result.fail(HCardConstants.FAIL);
			}else {
				return Result.fail("撤回失败，朋友早你一步领取了你，赠送的卡！！！");
			}
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public int pauseJob(Long jobId) {
		Long[] jonIds=new Long[1];
		jonIds[0]=jobId;
		return scheduleJobService.pause(jonIds);
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> activeHCard(Long hCardId) {
		String error_msg="激活失败";
		synchronized (hCardId) {
			try {
				UserHCardPO userHCardPO = userHCardDao.selectById(hCardId);
				if(userHCardPO!=null) {
					if(HCardConstants.STATE_NOT_ACTIVE.equals(userHCardPO.getState())||HCardConstants.STATE_FAIL_DONATION.equals(userHCardPO.getState())||HCardConstants.STATE_SUCCESS_RECEIVED.equals(userHCardPO.getState())) {
						// 1、改变卡状态
						Float hCardbalance =userHCardPO.getBalance();
						userHCardPO.setState(HCardConstants.STATE_ACTIVE);
						int num = userHCardDao.updateById(userHCardPO);
						// 2、将对应金额充值到用户心意卡余额
						if(num==1) {
							String openid=userHCardPO.getOpenid();
							// 查询我的心意卡余额
							UserHCardPursePO userHCardPursePO=hCardPurseService.getById(openid);
							
							if(userHCardPursePO==null) {
	                        	// 创建心意卡钱包
	                        	userHCardPursePO=new UserHCardPursePO();
	                        	userHCardPursePO.setOpenid(openid);
	                        	userHCardPursePO.setBalance(0f);
	                        	userHCardPursePO.setCreateTime(new Date());
	                        	boolean flag=hCardPurseService.save(userHCardPursePO);
	                        	if(!flag) {
	                        		error_msg="创建心意卡钱包" + "异常,"+" openid ="+openid;
	                        		throw new RRException(error_msg ,new Throwable());
	                        	}
	                        }
							
							Float balanceFront=userHCardPursePO.getBalance();
							userHCardPursePO.setBalance(hCardbalance);
							// 充值
							num = hCardPurseService.recharge(userHCardPursePO);
							if(num==1) {
								// 充值成功
								String hCardName=userHCardPO.getName();
								
								UserHCardBalanceLogPO hCardBalanceLogPO=new UserHCardBalanceLogPO();
								hCardBalanceLogPO.setOpenid(openid);
								hCardBalanceLogPO.setBalanceFront(balanceFront);
								hCardBalanceLogPO.setBalanceAfter(balanceFront+hCardbalance);
								hCardBalanceLogPO.setBalance(hCardbalance);
								hCardBalanceLogPO.setUseStatus(HCardBalanceLogConstants.USE_STATES_RECHARGE);
								hCardBalanceLogPO.setSourceType(HCardBalanceLogConstants.SOURCE_TYPE_ACTIVE);
								hCardBalanceLogPO.setRemark("心意卡激活");
								hCardBalanceLogPO.sethCardId(hCardId);
								hCardBalanceLogPO.sethCardName(hCardName);
								boolean flag=hCardBalanceLogService.insert(hCardBalanceLogPO);
								if(!flag) {
									error_msg="激活心意卡失败，增加日志记录" + "异常,"+" hCardId ="+hCardId;
									throw new RRException(error_msg,new Throwable());
								}
								return Result.success("激活成功");
							}else {
								// 抛出异常
								error_msg="激活心意卡失败，充值失败" + "异常,"+" hCardId ="+hCardId;
								throw new RRException( error_msg,new Throwable());
							}
						}
					}
					if(HCardConstants.STATE_ACTIVE.equals(userHCardPO.getState())) {
						return Result.fail("已经激活");
					}
					
				}else {
					return Result.fail("此卡不存在");
				}
			} catch (Exception e) {
				throw new RRException(error_msg ,e);
			}
		}
		return Result.fail(error_msg);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> activeHCard(ExpressDeliveryForm expressDeliveryForm) {
		String error_msg="激活失败";
		try {
			Long hCardId = expressDeliveryForm.getHCardId();
			synchronized (hCardId) {
				UserHCardPO userHCardPO=userHCardDao.selectById(hCardId);
				Integer state=userHCardPO.getState();

				if(HCardConstants.STATE_NOT_ACTIVE.equals(state)||HCardConstants.STATE_FAIL_DONATION.equals(state)) { //未激活
					// 3、激活礼品卡
					UserHCardPO hCardPO=new UserHCardPO();
					hCardPO.setId(userHCardPO.getId());
					hCardPO.setFetchTime(new Date());
					hCardPO.setState(HCardConstants.STATE_ACTIVE);
					int num=userHCardDao.updateById(hCardPO);
					if(num==0){
						error_msg = "激活礼品卡" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}

					// 5、添加快递信息
					DozerBeanMapper dozerBeanMapper=new DozerBeanMapper();
					ExpressDeliveryPO expressDeliveryPO=dozerBeanMapper.map(expressDeliveryForm ,ExpressDeliveryPO.class);
					expressDeliveryPO.setUserHcardId(expressDeliveryForm.getHCardId());
					boolean flag=expressDeliveryService.save(expressDeliveryPO);
					if(!flag){
						error_msg="填写快递信息"+"异常"+"hCardId="+hCardId;
						return  Result.fail(error_msg);
					}
					return Result.success("激活成功","激活成功");
				}else{
					return Result.fail("已激活！");
				}
			}
		}catch (Exception e){
			throw new RRException(error_msg,e);
		}

	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> refund(Long hCardId) {
		
		String error_msg="退款失败";
		
		synchronized (hCardId) {
			try {
				// 1、查询卡状态
	        	UserHCardPO userHCard = userHCardDao.selectById(hCardId);
	        	
	    		if(!HCardConstants.CHANNEL_SELF_BUY.equals(userHCard.getChannel())) {
	    			System.out.println("非购者，无法申请退款");
	    			return Result.fail("非购者，无法申请退款");
	    		}
	    		
	    		if(HCardConstants.STATE_REFUND==userHCard.getState()) {
	    			System.out.println("此卡已退款");
	    			return Result.fail("此卡已退款");
	    		}
	    		
	    		if(HCardConstants.STATE_NOT_ACTIVE!=userHCard.getState()) {
	    			System.out.println("此卡已使用，无法申请退款");
	    			return Result.fail("此卡已使用，无法申请退款");
	    		}
	    		
	    		// 2.1 查询退款记录
	    		QueryWrapper<HCardRefundLogPO> queryWrapper=new QueryWrapper<HCardRefundLogPO>();
	        	queryWrapper.eq("hCard_id", hCardId);
	        	queryWrapper.eq("state", 1);
	        	HCardRefundLogPO refundLogPO=refundLogService.getOne(queryWrapper);
	        	if(refundLogPO!=null){
	        		System.out.println("查询退款记录,此卡已退款");
	    			return Result.fail("查询退款记录,此卡已退款");
	        	}
	        	
	        	// 2.2 添加退卡日志
	    		refundLogPO=new HCardRefundLogPO();
	    		refundLogPO.setOpenid(userHCard.getOpenid());
	    		refundLogPO.setHcardId(userHCard.getId());
	    		refundLogPO.setOutTradeNo(userHCard.getOutTradeNo());
	    		refundLogPO.setAmount(userHCard.getBalance());
	    		refundLogPO.setCreateTime(new Date());
	    		refundLogPO.setState(1);
	    		boolean flag=refundLogService.save(refundLogPO);
	    		if(!flag){
					// 抛出异常
					error_msg="添加退卡日志" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}
	    		
	        	// 查询订单
	            String out_trade_no=userHCard.getOutTradeNo();
	            HCardOrderPO hCardOrderPO=hCardOrderService.getHCardOrderByOutTradeNo(out_trade_no);
	            
	            String openid=userHCard.getOpenid();
	            Integer total_fee = Math.round(hCardOrderPO.getTotalFee()*100);
	            Integer refund_fee =Math.round(userHCard.getBalance()*100);
	            // 3、从用户卡包中移除当前卡
	            userHCard.setState(HCardConstants.STATE_REFUND);
	            int num=userHCardDao.updateById(userHCard);
	            if(num==0){
					// 抛出异常
					error_msg="从用户卡包中移除当前卡" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}
				// 4、恢复库存
	            num=priceService.increaswHCardCount(userHCard.gethCardPriceId());
				if(num==0){
					// 抛出异常
					error_msg="从用户卡包中移除当前卡时,恢复库存" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}
					
				// 5、退款
				if(num==1){
					Map<String, String> data=wXOrder.refund(out_trade_no, openid, refund_fee,total_fee);
					if(WXPayConstants.SUCCESS.equals(data.get("return_code"))) {
						if(WXPayConstants.SUCCESS.equals(data.get("result_code"))) {
							return Result.success("退款成功","退款成功");
						}else {
							// 抛出异常
							error_msg="退款失败" + "异常,"+" hCardId ="+hCardId+" err_code_des="+data.get("err_code_des");
							throw new RRException(error_msg ,new Throwable());
						}
						
					}else {
						// 抛出异常
						error_msg="退款失败" + "异常,"+" hCardId ="+hCardId+" err_code_des="+data.get("return_msg");
						throw new RRException(error_msg,new Throwable());
					}
				}
			} catch (Exception e) {
				// 抛出异常
				throw new RRException(error_msg ,e);
			}
			
			return Result.fail(HCardConstants.FAIL);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> refund(Long hCardId ,Float refundFee) {
		String error_msg="退款失败";
		
		synchronized (hCardId) {
			try {
				UserHCardPO userHCard = userHCardDao.selectById(hCardId);
				
				if(!HCardConstants.CHANNEL_SELF_BUY.equals(userHCard.getChannel())) {
					return Result.fail("非购买本卡的用户，无法申请退款！！！");
				}
				if(HCardConstants.STATE_NOT_ACTIVE!=userHCard.getState()) {
					return Result.fail("此卡已经使用，无法申请退款了！！！");
				}
				
				// 1.添加退卡日志
	    		HCardRefundLogPO refundLogPO=new HCardRefundLogPO();
	    		refundLogPO.setOpenid(userHCard.getOpenid());
	    		refundLogPO.setHcardId(userHCard.getId());
	    		refundLogPO.setOutTradeNo(userHCard.getOutTradeNo());
	    		refundLogPO.setCreateTime(new Date());
	    		refundLogPO.setState(0);
	    		boolean flag=refundLogService.save(refundLogPO);
				// 查询订单
				String out_trade_no=userHCard.getOutTradeNo();
				HCardOrderPO hCardOrderPO=hCardOrderService.getHCardOrderByOutTradeNo(out_trade_no);
				
				String openid=userHCard.getOpenid();
				Integer total_fee = Math.round(hCardOrderPO.getTotalFee()*100);
				Integer refund_fee =Math.round(refundFee*100);
				// 2、从用户卡包中移除当前卡
				userHCard.setState(HCardConstants.STATE_REFUND);
				userHCard.setGiveTime(new Date());
				int num=userHCardDao.updateById(userHCard);
				
				if(num==0){
					// 抛出异常
					error_msg="从用户卡包中移除当前卡" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}
				// 3、恢复库存
				num=priceService.increaswHCardCount(userHCard.gethCardPriceId());
				if(num==0){
					// 抛出异常
					error_msg="从用户卡包中移除当前卡时,恢复库存" + "异常,"+" hCardId ="+hCardId;
					throw new RRException(error_msg ,new Throwable());
				}
				// 4、退款
				if(num==1){
					Map<String, String> resultMap=wXOrder.refund(out_trade_no, openid, refund_fee,total_fee);
					if(WXPayConstants.SUCCESS.equals(resultMap.get("return_code"))) {
						if(WXPayConstants.SUCCESS.equals(resultMap.get("result_code"))) {
							return Result.success("退款成功");
						}else {
							// 抛出异常
							error_msg="退款失败" + "异常,"+" hCardId ="+hCardId+" err_code_des="+resultMap.get("err_code_des");
							throw new RRException(error_msg ,new Throwable());
						}
						
					}else {
						// 抛出异常
						error_msg="退款失败" + "异常,"+" hCardId ="+hCardId+" return_msg="+resultMap.get("return_msg");
						throw new RRException(error_msg,new Throwable());
					}
				}
			} catch (Exception e) {
				// 抛出异常
				throw new RRException(error_msg ,e);
			}
		}
		return Result.fail(error_msg);
	}
	
	
	@Override
	public int updateHCardStateById(Long hCardId ,Integer state ,Date date){
		UserHCardPO userHCardPO=new UserHCardPO();
		userHCardPO.setId(hCardId);
		userHCardPO.setState(state);
		userHCardPO.setGiveTime(date);
		return userHCardDao.updateById(userHCardPO);
	}

	@Override
	public List<UserHCardDTO> getBuyedCount(Map map) {
		// TODO Auto-generated method stub
		return userHCardDao.getBuyedCount(map);
	}

	@Override
	public List<UserHCardPO> getReceiveRecord(Map map) {
		// TODO Auto-generated method stub
		return userHCardDao.getReceiveRecord(map);
	}

	@Override
	public List<UserHCardPO> getBuyRecord(String openid) {
		// TODO Auto-generated method stub
		return userHCardDao.getBuyRecord(openid);
	}

	@Override
	public List<UserHCardPO> getGiveRecord(Map map) {
		// TODO Auto-generated method stub
		return userHCardDao.getGiveRecord(map);
	}

	@Override
	public List<UserHCardPO> getRefundRecord(Map map) {
		// TODO Auto-generated method stub
		return userHCardDao.getRefundRecord(map);
	}

	@Override
	public List<ExpressDeliveryVO> getUsedHCard(Map map) {
		return userHCardDao.getUsedHCard(map);
	}


}
