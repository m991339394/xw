package io.renren.modules.app.model.form;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**   
 * @ClassName:  HCardPriceVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年9月29日 下午3:37:40   
 */
@Data
public class HCardPriceForm {
	
	@ApiModelProperty(value = "心意卡价格id")
    @TableField("hCard_type_id")
    private Long hcardPriceId;
	
	@ApiModelProperty(value = "购买心意卡数量")
    private Long count;

	public Long getHcardPriceId() {
		return hcardPriceId;
	}

	public void setHcardPriceId(Long hcardPriceId) {
		this.hcardPriceId = hcardPriceId;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "HCardPriceVO [hcardPriceId=" + hcardPriceId + ", count=" + count + "]";
	}
	
	
	
}
