package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.app.dao.UserHCardTypeDao;
import io.renren.modules.app.model.po.HCardTypePO;
import io.renren.modules.app.service.UserHCardTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 心意卡类型 服务实现类
 * </p>
 *
 * @author cth
 * @since 2019-09-27
 */
@Service
public class UserHCardTypeServiceImpl extends ServiceImpl<UserHCardTypeDao, HCardTypePO> implements UserHCardTypeService {

}
