package io.renren.modules.app.service.impl;

import io.renren.modules.app.model.po.HCardOrderPO;
import io.renren.modules.app.dao.AdminHCardOrderDao;
import io.renren.modules.app.service.AdminHCardOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户购买心意卡订单记录 服务实现类
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@Service
public class AdminHCardOrderServiceImpl extends ServiceImpl<AdminHCardOrderDao, HCardOrderPO> implements AdminHCardOrderService {

}
