package io.renren.modules.app.model.vo;


import io.renren.modules.app.model.po.HCardOrderPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**   
 * @ClassName:  HCardOrderVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年10月19日 下午1:34:50   
 */
@Data
public class HCardOrderVO extends HCardOrderPO {
	
	@ApiModelProperty(value = "用户心意卡优惠卷id")
    private Long hCardCouponId;

	public Long gethCardCouponId() {
		return hCardCouponId;
	}

	public void sethCardCouponId(Long hCardCouponId) {
		this.hCardCouponId = hCardCouponId;
	}
	

}
