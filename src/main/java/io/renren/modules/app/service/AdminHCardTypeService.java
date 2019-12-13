package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.Result;
import io.renren.modules.app.model.po.HCardTypePO;

/**
 * <p>
 * 心意卡类型 服务类
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
public interface AdminHCardTypeService extends IService<HCardTypePO> {
	
	Result<?> upperSheftAndLowerSheft(HCardTypePO hCardTypePO);



}
