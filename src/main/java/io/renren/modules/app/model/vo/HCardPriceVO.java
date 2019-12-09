package io.renren.modules.app.model.vo;

import io.renren.modules.app.model.po.HCardPricePO;
import lombok.Data;

/**   
 * @ClassName:  HCardPriceVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年10月21日 上午10:11:06   
 */
@Data
public class HCardPriceVO extends HCardPricePO {
	// 用户购买数量
	private int buyedCount;

	public int getBuyedCount() {
		return buyedCount;
	}

	public void setBuyedCount(int buyedCount) {
		this.buyedCount = buyedCount;
	}

	
}
