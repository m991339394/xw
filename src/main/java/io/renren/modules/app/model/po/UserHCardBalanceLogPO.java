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
 * 用户余额/积分变动纪录表
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ggwl_user_HCard_balance_log")
@ApiModel(value="UserHCardBalanceLogPO对象", description="用户余额/积分变动纪录表")
public class UserHCardBalanceLogPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "用户ID")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty(value = "余额，变动【前】数值")
    @TableField("balanceFront")
    private Float balanceFront;

    @ApiModelProperty(value = "余额，变动【后】数值")
    @TableField("balanceAfter")
    private Float balanceAfter;

    @ApiModelProperty(value = "消费金额")
    @TableField("balance")
    private Float balance;

    @ApiModelProperty(value = "状态  1新增 2消耗")
    @TableField("useStatus")
    private Integer useStatus;

    @ApiModelProperty(value = "来源类型 1 心意卡激活 2 购买商品3 订单退款")
    @TableField("sourceType")
    private Integer sourceType;

    @ApiModelProperty(value = "订单编号")
    @TableField("orderId")
    private String orderId;

    @ApiModelProperty(value = "订单类型：1美食, 2MTX电影，3旅游，4淘票票电影，5影吧")
    @TableField("orderType")
    private Integer orderType;

    @ApiModelProperty(value = "变动说明（购买赠送积分等。。）")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "变动时间")
    @TableField("useTime")
    private Date useTime;

    @ApiModelProperty(value = "心意卡id")
    @TableField("hCardId")
    private Long hCardId;

    @ApiModelProperty(value = "心意卡名称")
    @TableField("hCardName")
    private String hCardName;

    @ApiModelProperty(value = "消费人")
    @TableField("consumer")
    private String consumer;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Float getBalanceFront() {
		return balanceFront;
	}

	public void setBalanceFront(Float balanceFront) {
		this.balanceFront = balanceFront;
	}

	public Float getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(Float balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Long gethCardId() {
		return hCardId;
	}

	public void sethCardId(Long hCardId) {
		this.hCardId = hCardId;
	}

	public String gethCardName() {
		return hCardName;
	}

	public void sethCardName(String hCardName) {
		this.hCardName = hCardName;
	}

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
