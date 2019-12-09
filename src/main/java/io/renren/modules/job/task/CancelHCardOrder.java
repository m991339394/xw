package io.renren.modules.job.task;

import com.alibaba.fastjson.JSONArray;
import io.renren.common.Constants.HCardConstants;
import io.renren.common.Constants.ScheduleJobConstants;
import io.renren.common.exception.RRException;
import io.renren.modules.app.model.form.HCardPriceForm;
import io.renren.modules.app.model.po.HCardOrderPO;
import io.renren.modules.app.model.po.HCardPricePO;
import io.renren.modules.app.service.UserHCardOrderService;
import io.renren.modules.app.service.UserHCardPriceService;
import io.renren.modules.app.service.thirdParty.wxSdk.WXOrder;
import io.renren.modules.job.service.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**   
 * @ClassName:  CancelHCardOrder   
 * @Description:时间到了用户未支付，关闭订单任务   
 * @author: jgl
 * @date:   2019年10月7日 下午2:54:08   
 */
@Component(ScheduleJobConstants.CANCEL_ORDER_BEAN_NAME)
public class CancelHCardOrder implements ITask{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserHCardOrderService orderService;
	@Autowired
	WXOrder wxOrder;
	@Autowired
	ScheduleJobService scheduleJobService;
	@Autowired
	UserHCardPriceService priceService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run(String params){
		String[] arrayStr=params.split(ScheduleJobConstants.SEPARATOR);
		Long hCardId=Long.parseLong(arrayStr[1]);
		String outTradeNo=arrayStr[2];
		String error_msg="";
		synchronized (hCardId) {
			HCardOrderPO hCardOrderPO = orderService.getById(hCardId);
			Long jobId=hCardOrderPO.getScheduleId();
			
			if(hCardOrderPO.getState()== HCardConstants.ORDER_UNPAID){ // 订单未支付
				// 1、关闭订单
				wxOrder.cancelHCardOrder(outTradeNo);
				// 2、恢复商品数量
				String priceList=hCardOrderPO.getHcardPriceIds();
				// 用户选购的商品
				List<HCardPriceForm> hCardPriceForms=JSONArray.parseArray(priceList, HCardPriceForm.class);
				// 库存
				List<HCardPricePO> hCardPricePOs=priceService.findListByIds(priceList);
				// 恢复商品数量
				int num=priceService.increaswHCardCount(hCardPriceForms, outTradeNo);
				// 3、修改订单状态
				if(num==1) {
					num=orderService.updateOrderState(hCardId,HCardConstants.ORDER_CANCEL);
					if(num==0) {
						error_msg="修改订单状态" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}
				}
				// 4、停止定时任务
				if(num==1) {
					num=scheduleJobService.pause(jobId);
					if(num==0) {
						error_msg="停止定时任务" + "异常,"+" hCardId ="+hCardId;
						throw new RRException(error_msg ,new Throwable());
					}
				}
			}
			
		}
	}
}
