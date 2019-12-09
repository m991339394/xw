package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.app.model.form.UserHCardForm;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.model.vo.UserHCardVO;

import java.util.List;

/**
 * <p>
 * 用户心意卡 服务类
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
public interface AdminUserHCardService extends IService<UserHCardPO> {
	
	List<UserHCardVO> getBuyRecord(UserHCardForm userHCardForm);

	List<UserHCardVO> getConsumeRecord(UserHCardForm userHCardForm);

	List<UserHCardVO> getGiveRecord(UserHCardForm userHCardForm);
	
	List<UserHCardVO> getActivedHCard(UserHCardForm userHCardForm);
	
	List<UserHCardPO> getRefundRecord(UserHCardForm userHCardForm);

}
