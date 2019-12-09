package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.Result;
import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import io.renren.modules.app.model.po.UserHCardPursePO;

/**
 * <p>
 * 用户心意卡钱包 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
public interface UserUserHCardPurseService extends IService<UserHCardPursePO> {
	// 充值
	int recharge(UserHCardPursePO userHCardPursePO);
	// 支付
	Result<?> pay(UserHCardBalanceLogPO hCardBalanceLogPO);
	// 退款
	Result<?> refund(UserHCardBalanceLogPO hCardBalanceLogPO);
	
}
