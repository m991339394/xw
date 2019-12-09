package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.app.dao.UserExpressDeliveryDao;
import io.renren.modules.app.model.po.ExpressDeliveryPO;
import io.renren.modules.app.service.UserExpressDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 快递单号 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-11-26
 */
@Service
public class UserExpressDeliveryServiceImpl extends ServiceImpl<UserExpressDeliveryDao, ExpressDeliveryPO> implements UserExpressDeliveryService {
    @Autowired
    UserExpressDeliveryDao expressDeliveryDao;
    @Override
    public ExpressDeliveryPO getExpressDeliveryByHCardId(Long hCardId) {
        QueryWrapper<ExpressDeliveryPO> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_hCard_id",hCardId);
        return expressDeliveryDao.selectOne(queryWrapper);
    }
}
