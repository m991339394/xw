package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.Result;
import io.renren.modules.app.model.dto.UserHCardDTO;
import io.renren.modules.app.model.form.ExpressDeliveryForm;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.model.vo.ExpressDeliveryVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户心意卡 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-10-04
 */
public interface UserUserHCardService extends IService<UserHCardPO> {

	int updateHCardStateById(Long hCardId, Integer state);

	int updateHCardStateById(Long hCardId, Integer state , Date date);

//	Result<?> giveHCardToFriend(Long hCardId,String state);

	Result<?> giveHCardToFriend(Long hCardId, String openId, String uuid);
//	Result<?> lookFriendToMeHCard(Long hCardId ,String openid );

	Result<?> lookFriendToMeHCard(Long hCardId, String openid, String uuid);

	Result<?> receiveFriendToMeHCard(Long hCardId, String myOpenid);

	Result<?> receiveFriendToMeHCard(ExpressDeliveryForm expressDeliveryForm);

	Result<?> cancelGiveHCardToFriend(Long hCardId, Integer state);

	Result<?> activeHCard(Long hCardId);

	Result<?> activeHCard(ExpressDeliveryForm expressDeliveryForm);

	Result<?> refund(Long hCardId);

	Result<?> refund(Long hCardId, Float refund_fee);

	List<UserHCardDTO> getBuyedCount(Map map);
	
	List<UserHCardPO> getReceiveRecord(Map map);
	
	List<UserHCardPO> getBuyRecord(String openid);
	
	List<UserHCardPO> getGiveRecord(Map map);
	
	List<UserHCardPO> getRefundRecord(Map map);

	List<ExpressDeliveryVO> getUsedHCard(Map map);
}
