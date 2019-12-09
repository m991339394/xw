package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.app.dao.UserUserHCardBalanceLogDao;
import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import io.renren.modules.app.service.UserUserHCardBalanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户余额/积分变动纪录表 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@Service
public class UserUserHCardBalanceLogServiceImpl extends ServiceImpl<UserUserHCardBalanceLogDao, UserHCardBalanceLogPO> implements UserUserHCardBalanceLogService {
	@Autowired
	UserUserHCardBalanceLogDao hCardBalanceLogDao;
	@Override
	public boolean insert(UserHCardBalanceLogPO userHCardBalanceLogPO) {
		// TODO Auto-generated method stub
		userHCardBalanceLogPO.setUseTime(new Date());
		int num=hCardBalanceLogDao.insert(userHCardBalanceLogPO);
		return num>0?true:false;
	}
	@Override
	public List<UserHCardBalanceLogPO> getUserHCardBalanceLogByUseStatus(Map map) {
		// TODO Auto-generated method stub
		return hCardBalanceLogDao.getUserHCardBalanceLogByUseStatus(map);
	}
	
	

}
