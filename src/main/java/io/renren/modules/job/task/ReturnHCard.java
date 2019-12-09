package io.renren.modules.job.task;

import io.renren.common.Constants.HCardConstants;
import io.renren.common.Constants.HCardGiveLogConstants;
import io.renren.common.Constants.ScheduleJobConstants;
import io.renren.common.exception.RRException;
import io.renren.modules.app.model.po.HCardGiveLogPO;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.service.UserHCardGiveLogService;
import io.renren.modules.app.service.UserUserHCardService;
import io.renren.modules.job.service.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 时间到了朋友未领取心意卡，返还给用户
 *
 * returnHCard为spring bean的名称
 *
 */
@Component(ScheduleJobConstants.RETURN_HCARD_BEAN_NAME)
public class ReturnHCard implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserUserHCardService userHCardService;
	@Autowired
	ScheduleJobService scheduleJobService;
	@Autowired
	UserHCardGiveLogService giveLogService;

	@Override
	public void run(String params){
		String[] arrayStr=params.split(ScheduleJobConstants.SEPARATOR);
		Long hCardId=Long.parseLong(arrayStr[1]);
		Long jobId=Long.parseLong(arrayStr[2]);
		String uuid=arrayStr[3];
		String error_msg="";
		synchronized (hCardId) {
			UserHCardPO userHCardPO=userHCardService.getById(hCardId);
			if(HCardConstants.STATE_IN_DONATION.equals(userHCardPO.getState())){ // 转赠中
				// 取消转赠
				Date date=new Date();
				int num=userHCardService.updateHCardStateById(hCardId ,HCardConstants.STATE_FAIL_DONATION);
				// 1、修改转赠记录(转赠退回)
				if(num==1) {
					HCardGiveLogPO hCardGiveLogPO=giveLogService.getById(uuid);
					if(hCardGiveLogPO!=null) {
						num=giveLogService.updateGiveLogByHCardId(uuid, HCardGiveLogConstants.RETURN_DONATION ,date);
						if(num==0) {
							error_msg="修改转赠记录" + "异常,"+" hCardId ="+hCardId;
							throw new RRException(error_msg ,new Throwable());
						}
					}
				}
				if(num==1) {
					// 2、关闭定时任务
					num=scheduleJobService.pause(jobId);
					if(num==0) {
						error_msg="关闭定时任务" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}
				}
			}
		}
	}
}
