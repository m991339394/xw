package io.renren.modules.app.model.vo;

import io.renren.modules.app.model.po.HCardCouponPO;
import lombok.Data;

/**   
 * @ClassName:  HCardCouponVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年10月19日 上午10:13:56   
 */
@Data
public class HCardCouponVO extends HCardCouponPO {
	private int pageNo;
	private int pageSize;
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNum(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
