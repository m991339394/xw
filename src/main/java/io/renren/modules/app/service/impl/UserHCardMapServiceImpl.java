package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.app.dao.UserHCardMapDao;
import io.renren.modules.app.model.po.HCardMapPO;
import io.renren.modules.app.service.UserHCardMapService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 心意卡卡面图 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-09-27
 */
@Service
public class UserHCardMapServiceImpl extends ServiceImpl<UserHCardMapDao, HCardMapPO> implements UserHCardMapService {

}
