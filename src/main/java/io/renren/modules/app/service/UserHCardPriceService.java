package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.app.model.form.HCardPriceForm;
import io.renren.modules.app.model.po.HCardPricePO;

import java.util.List;

/**
 * <p>
 * 心意卡 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
public interface UserHCardPriceService extends IService<HCardPricePO> {
	
	int increaswHCardCount(List<HCardPriceForm> hCardPriceForms, String out_trade_no);
	
	int increaswHCardCount(Long hCardPriceId);
	
	int subHCardCount(List<HCardPriceForm> hCardPriceForms);
	
	int subHCardCount(List<HCardPriceForm> hCardPriceForms, String out_trade_no);
	
	List<HCardPricePO> findListByIds(String priceList);
}
