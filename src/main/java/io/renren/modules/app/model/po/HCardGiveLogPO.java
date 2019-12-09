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
 * 心意卡转赠记录表
 * </p>
 *
 * @author jgl
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ggwl_HCard_give_log")
@ApiModel(value="HCardGiveLogPO对象", description="心意卡转赠记录表")
public class HCardGiveLogPO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "订单号")
    @TableId(value = "uuid", type = IdType.INPUT)
    private String uuid;

    @ApiModelProperty(value = "赠卡者openid")
    @TableField("give_openid")
    private String giveOpenid;
    
    @ApiModelProperty(value = "领卡者openid")
    @TableField("receive_openid")
    private String receiveOpenid;

    @ApiModelProperty(value = "心意卡id")
    @TableField(value = "hCard_id")
    private Long hcardId;


    @ApiModelProperty(value = "转赠时间(或激活时间)")
    @TableField("give_time")
    private Date giveTime;

    @ApiModelProperty(value = "领取时间")
    @TableField("receive_time")
    private Date receiveTime;

    @ApiModelProperty(value = "领取(0未领取 ，1已领取  ,2转赠退回)")
    @TableField("state")
    private Integer state;
    

	public String getGiveOpenid() {
		return giveOpenid;
	}

	public void setGiveOpenid(String giveOpenid) {
		this.giveOpenid = giveOpenid;
	}

	public String getReceiveOpenid() {
		return receiveOpenid;
	}

	public void setReceiveOpenid(String receiveOpenid) {
		this.receiveOpenid = receiveOpenid;
	}

	public Long getHcardId() {
		return hcardId;
	}

	public void setHcardId(Long hcardId) {
		this.hcardId = hcardId;
	}

	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(Date giveTime) {
		this.giveTime = giveTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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
