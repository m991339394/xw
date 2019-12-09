package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.Result;
import io.renren.modules.app.model.form.HCardOrderForm;
import io.renren.modules.app.model.po.HCardOrderPO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户购买心意卡订单记录 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
public interface UserHCardOrderService extends IService<HCardOrderPO> {
	
	Result<?> getHCardOrder(HCardOrderPO hCardOrderVO);
	
	Result<?> getHCardOrder(HttpServletRequest request, HCardOrderForm hCardOrderForm);
	
	Result<?> successfulPaymentNotify(String out_trade_no);
	
	int updateOrderState(Long out_trade_no_id);
	
	int updateOrderState(Long out_trade_no_id, Integer state);
	
	Result<?> closeOrder(Long id);
	
	Result<?> refund(String out_trade_no);
	
	HCardOrderPO getHCardOrderByOutTradeNo(String out_trade_no);
	
}
