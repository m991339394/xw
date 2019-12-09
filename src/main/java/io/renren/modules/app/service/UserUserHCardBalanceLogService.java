package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户余额/积分变动纪录表 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
public interface UserUserHCardBalanceLogService extends IService<UserHCardBalanceLogPO> {
	
	boolean insert(UserHCardBalanceLogPO userHCardBalanceLogPO);
	
	List<UserHCardBalanceLogPO> getUserHCardBalanceLogByUseStatus(Map map);


}
