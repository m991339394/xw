package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.dto.UserHCardDTO;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.model.vo.ExpressDeliveryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户心意卡 Mapper 接口
 * </p>
 *
 * @author jgl
 * @since 2019-10-04
 */
@Mapper
public interface UserUserHCardDao extends BaseMapper<UserHCardPO> {
	
	int insertBatch(List<UserHCardPO> userHCardPOs);
	
	List<UserHCardDTO> getBuyedCount(Map map);
	
	List<UserHCardPO> getBuyRecord(String openid);
	
	List<UserHCardPO> getGiveRecord(Map map);
	
	List<UserHCardPO> getReceiveRecord(Map map);
	
	List<UserHCardPO> getRefundRecord(Map map);

	List<ExpressDeliveryVO> getUsedHCard(Map map);

}
