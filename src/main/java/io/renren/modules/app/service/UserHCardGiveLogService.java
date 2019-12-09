package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.app.model.po.HCardGiveLogPO;

import java.util.Date;

/**
 * <p>
 * 心意卡转赠记录表 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-10-24
 */
public interface UserHCardGiveLogService extends IService<HCardGiveLogPO> {

	int updateGiveLogByHCardId(String uuid, int state , Date date);

}
