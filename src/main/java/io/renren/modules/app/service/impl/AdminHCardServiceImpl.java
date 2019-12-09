package io.renren.modules.app.service.impl;

import io.renren.modules.app.model.po.HCardPO;
import io.renren.modules.app.dao.AdminHCardDao;
import io.renren.modules.app.service.AdminHCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 心意卡 服务实现类
 * </p>
 *
 * @author cth
 * @since 2019-10-12
 */
@Service
public class AdminHCardServiceImpl extends ServiceImpl<AdminHCardDao, HCardPO> implements AdminHCardService {

}
