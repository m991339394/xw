package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.app.model.po.HCardRefundLogPO;
import io.renren.modules.app.model.po.UserHCardPO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 心意卡转赠记录表 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-11-19
 */
public interface UserHCardRefundLogService extends IService<HCardRefundLogPO> {
	
	List<UserHCardPO> getRefundRecord(Map map);
}
