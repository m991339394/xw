package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.Constants.HCardConstants;
import io.renren.common.Constants.HCardTypeConstants;
import io.renren.common.Result;
import io.renren.modules.app.dao.AdminHCardPriceDao;
import io.renren.modules.app.dao.AdminHCardTypeDao;
import io.renren.modules.app.model.po.HCardPricePO;
import io.renren.modules.app.model.po.HCardTypePO;
import io.renren.modules.app.service.AdminHCardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 心意卡类型 服务实现类
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@Service
public class AdminHCardTypeServiceImpl extends ServiceImpl<AdminHCardTypeDao, HCardTypePO> implements AdminHCardTypeService {
	@Autowired
	AdminHCardTypeDao typeDao;
	@Autowired
	AdminHCardPriceDao  priceDao;
	@Override
	public Result<?> upperSheftAndLowerSheft(HCardTypePO hCardTypePO) {
		// TODO Auto-generated method stub
		int upperSheft=hCardTypePO.getUpperShelf();
		// 下架
		if(upperSheft== HCardTypeConstants.LOWER_SHELF) {
			int num=typeDao.updateById(hCardTypePO);
			if(num==1) {
				UpdateWrapper<HCardPricePO> uWrapper=new UpdateWrapper<HCardPricePO>();
				uWrapper.eq("hCard_type_id", hCardTypePO.getId());
				uWrapper.set("upper_shelf", HCardConstants.LOWER_SHELF);
				num=priceDao.update(new HCardPricePO(), uWrapper);
			}
			return num>0?Result.success():Result.fail();
		}else {
			// 上架
			int num=typeDao.updateById(hCardTypePO);
			return num>0?Result.success():Result.fail();
		}
		
	}

}
