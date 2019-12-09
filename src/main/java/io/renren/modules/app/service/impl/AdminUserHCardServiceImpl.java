package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.Constants.HCardConstants;
import io.renren.modules.app.dao.AdminUserHCardDao;
import io.renren.modules.app.model.form.UserHCardForm;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.model.vo.UserHCardVO;
import io.renren.modules.app.service.AdminUserHCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户心意卡 服务实现类
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@Service
public class AdminUserHCardServiceImpl extends ServiceImpl<AdminUserHCardDao, UserHCardPO> implements AdminUserHCardService {
	@Autowired
	AdminUserHCardDao userHCardDao;
	
	@Override
	public List<UserHCardVO> getBuyRecord(UserHCardForm userHCardForm) {
		// TODO Auto-generated method stub
		return userHCardDao.getBuyRecord(userHCardForm);
	}

	@Override
	public List<UserHCardVO> getConsumeRecord(UserHCardForm userHCardForm) {
		return userHCardDao.getConsumeRecord(userHCardForm);
	}

	@Override
	public List<UserHCardVO> getGiveRecord(UserHCardForm userHCardForm) {
		// TODO Auto-generated method stub
		return userHCardDao.getGiveRecord(userHCardForm);
	}

	@Override
	public List<UserHCardVO> getActivedHCard(UserHCardForm userHCardForm) {
		// TODO Auto-generated method stub
		List<UserHCardVO> list=userHCardDao.getActivedHCard(userHCardForm);

		for (int i = 0; i < list.size(); i++) {
			UserHCardVO userHCardVO=list.get(i);
			Integer state=userHCardVO.getState();

			if(HCardConstants.STATE_NOT_ACTIVE.equals(state)){
				userHCardVO.setStateString(HCardConstants.STATE_NOT_ACTIVE_STRING);
			}else if(HCardConstants.STATE_ACTIVE.equals(state)) {
				list.get(i).setStateString(HCardConstants.STATE_ACTIVE_STRING);
			}else if(HCardConstants.STATE_IN_DONATION.equals(state)) {
				userHCardVO.setStateString(HCardConstants.STATE_IN_DONATION_STRING);
			}else if(HCardConstants.STATE_FAIL_DONATION.equals(state)) {
				userHCardVO.setStateString(HCardConstants.STATE_FAIL_DONATION_STRING);
			}else if(HCardConstants.STATE_SUCCESS_DONATION.equals(state)) {
				userHCardVO.setStateString(HCardConstants.STATE_SUCCESS_DONATION_STRING);
			}else if(HCardConstants.STATE_REFUND.equals(state)) {
				userHCardVO.setStateString(HCardConstants.STATE_REFUND_STRING);
			}else if(HCardConstants.STATE_SUCCESS_RECEIVED.equals(state)) {
				userHCardVO.setStateString(HCardConstants.STATE_SUCCESS_RECEIVED_STRING);
			}else if(HCardConstants.STATE_SUCCESS_OPEN.equals(state)) {
				userHCardVO.setStateString(HCardConstants.STATE_SUCCESS_OPEN_STRING);
			}
		}
		return list;
	}

	@Override
	public List<UserHCardPO> getRefundRecord(UserHCardForm userHCardForm) {
		// TODO Auto-generated method stub
		return userHCardDao.getRefundRecord(userHCardForm);
	}

}
