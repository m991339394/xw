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

import java.sql.Timestamp;

/**
 * <p>
 * 用户的心意卡优惠券
 * </p>
 *
 * @author jgl
 * @since 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ggwl_user_coupon")
@ApiModel(value="UserCouponPO对象", description="用户的心意卡优惠券")
public class UserCouponPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "主题")
    @TableField("theme")
    private String theme;

    @ApiModelProperty(value = "图")
    @TableField("img")
    private String img;

    @ApiModelProperty(value = "金额")
    @TableField("money")
    private Float money;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "期限")
    @TableField("time_limit")
    private Integer timeLimit;

    @ApiModelProperty(value = "使用范围")
    @TableField("use_scope")
    private String useScope;

    @ApiModelProperty(value = "领取时间")
    @TableField("created_time")
    private Timestamp createdTime;

    @ApiModelProperty(value = "过期时间")
    @TableField("expire_time")
    private Timestamp expireTime;

    @ApiModelProperty(value = "使用时间")
    @TableField("use_time")
    private Timestamp useTime;

    @ApiModelProperty(value = "用户openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "订单号")
    @TableField("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "价格id")
    @TableField("hCard_price_id")
    private Long hcardPriceId;
    
    @ApiModelProperty(value = "优惠卷id")
    @TableField("hCard_coupon_id")
    private Long hCardCoupon_Id;
    
    
    @ApiModelProperty(value = "状态（0未使用，1已使用）")
    @TableField("state")
    private Integer state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getUseScope() {
		return useScope;
	}

	public void setUseScope(String useScope) {
		this.useScope = useScope;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Timestamp expireTime) {
		this.expireTime = expireTime;
	}

	public Timestamp getUseTime() {
		return useTime;
	}

	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Long getHcardPriceId() {
		return hcardPriceId;
	}

	public void setHcardPriceId(Long hcardPriceId) {
		this.hcardPriceId = hcardPriceId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long gethCardCoupon_Id() {
		return hCardCoupon_Id;
	}

	public void sethCardCoupon_Id(Long hCardCoupon_Id) {
		this.hCardCoupon_Id = hCardCoupon_Id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    

}
