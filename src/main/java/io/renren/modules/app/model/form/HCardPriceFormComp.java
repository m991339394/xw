package io.renren.modules.app.model.form;

import java.util.Comparator;

/**   
 * @ClassName:  HCardPriceVOComp   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年10月15日 下午5:51:54   
 */
public class HCardPriceFormComp implements Comparator<HCardPriceForm> {

	@Override
	public int compare(HCardPriceForm o1, HCardPriceForm o2) {
		// TODO Auto-generated method stub
		return (o1.getHcardPriceId()-o2.getHcardPriceId()>0 ? 1 : (o1.getHcardPriceId()==o2.getHcardPriceId())?0 : -1);
	}

}
