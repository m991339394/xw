package io.renren.modules.app.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 心意卡购买记录表
 * </p>
 *
 * @author jgl
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ggwl_HCard_buy_log")
@ApiModel(value="HCardBuyLogPO对象", description="心意卡购买记录表")
public class HCardBuyLogPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "心意卡id")
    @TableField("hCard_id")
    private Long hcardId;
    
    @ApiModelProperty(value = "心意卡价格id")
    @TableField("hCard_price_id")
    private Long hCardPriceId;

    @ApiModelProperty(value = "订单号")
    @TableField("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @TableField("state")
    private Integer state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getHcardId() {
		return hcardId;
	}

	public void setHcardId(Long hcardId) {
		this.hcardId = hcardId;
	}

	public Long gethCardPriceId() {
		return hCardPriceId;
	}

	public void sethCardPriceId(Long hCardPriceId) {
		this.hCardPriceId = hCardPriceId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
