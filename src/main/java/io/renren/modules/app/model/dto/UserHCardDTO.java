package io.renren.modules.app.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**   
 * @ClassName:  UserHCardDTO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年10月18日 下午9:25:01   
 */
@Data
public class UserHCardDTO {
	
	@ApiModelProperty(value = "心意卡价格id")
    @TableField("hCard_type_id")
	private Long hCardPriceId;
	
	@ApiModelProperty(value = "购买心意卡数量")
	private Integer count;
	
	public Long gethCardPriceId() {
		return hCardPriceId;
	}
	public void sethCardPriceId(Long hCardPriceId) {
		this.hCardPriceId = hCardPriceId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
