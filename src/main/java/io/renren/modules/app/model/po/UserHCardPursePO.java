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
 * 用户心意卡钱包
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ggwl_user_HCard_purse")
@ApiModel(value="UserHCardPursePO对象", description="用户心意卡钱包")
public class UserHCardPursePO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "openid")
    @TableId(value = "openid", type = IdType.INPUT)
    private String openid;
    
    @ApiModelProperty(value = "userid")
    @TableField(value = "userId")
    private Integer userId;

    @ApiModelProperty(value = "余额")
    @TableField("balance")
    private Float balance;

    @ApiModelProperty(value = "积分")
    @TableField("integralBalance")
    private Float integralBalance;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateTime")
    private Date updateTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Float getIntegralBalance() {
		return integralBalance;
	}

	public void setIntegralBalance(Float integralBalance) {
		this.integralBalance = integralBalance;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
