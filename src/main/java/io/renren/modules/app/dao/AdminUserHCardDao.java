package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.model.form.UserHCardForm;
import io.renren.modules.app.model.po.UserHCardPO;
import io.renren.modules.app.model.vo.UserHCardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户心意卡 Mapper 接口
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@Mapper
public interface AdminUserHCardDao extends BaseMapper<UserHCardPO> {

	List<UserHCardVO> getBuyRecord(UserHCardForm userHCardForm);

	List<UserHCardVO> getConsumeRecord(UserHCardForm userHCardForm);

	List<UserHCardVO> getGiveRecord(UserHCardForm userHCardForm);

	List<UserHCardVO> getActivedHCard(UserHCardForm userHCardForm);
	
	List<UserHCardPO> getRefundRecord(UserHCardForm userHCardForm);
	
}
