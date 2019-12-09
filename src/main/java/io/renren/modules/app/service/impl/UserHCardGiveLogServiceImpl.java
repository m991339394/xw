package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.app.dao.UserHCardGiveLogDao;
import io.renren.modules.app.model.po.HCardGiveLogPO;
import io.renren.modules.app.service.UserHCardGiveLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 心意卡转赠记录表 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-10-24
 */
@Service
public class UserHCardGiveLogServiceImpl extends ServiceImpl<UserHCardGiveLogDao, HCardGiveLogPO> implements UserHCardGiveLogService {
	@Autowired
	UserHCardGiveLogDao giveLogDao;
	
	@Override
	public int updateGiveLogByHCardId(String uuid ,int state ,Date date) {
		// 修改转赠记录
		HCardGiveLogPO hCardGiveLogPO=new HCardGiveLogPO();
		hCardGiveLogPO.setUuid(uuid);
		hCardGiveLogPO.setState(state);
		hCardGiveLogPO.setReceiveTime(date);
		return giveLogDao.updateById(hCardGiveLogPO);
	}
	
}
