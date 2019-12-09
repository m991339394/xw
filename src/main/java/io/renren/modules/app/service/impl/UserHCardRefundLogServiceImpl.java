package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.app.dao.UserHCardRefundLogDao;
import io.renren.modules.app.model.po.HCardRefundLogPO;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.service.UserHCardRefundLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 心意卡转赠记录表 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-11-19
 */
@Service
public class UserHCardRefundLogServiceImpl extends ServiceImpl<UserHCardRefundLogDao, HCardRefundLogPO> implements UserHCardRefundLogService {
	@Autowired
	UserHCardRefundLogDao refundLogDao;

	@Override
	public List<UserHCardPO> getRefundRecord(Map map) {
		// TODO Auto-generated method stub
		return refundLogDao.getrRefundRecord(map);
	}
}
