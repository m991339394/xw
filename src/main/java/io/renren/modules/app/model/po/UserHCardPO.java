package io.renren.modules.app.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 心意卡明细字段列表
 * </p>
 *
 * @author jgl
 * @since 2019-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ggwl_user_HCard")
@ApiModel(value="UserHCardPO对象", description="心意卡明细字段列表")
public class UserHCardPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "心意卡类型id")
    @TableField(value ="hCard_type_id")
    private Long hcardTypeId;

    @ApiModelProperty(value = "用户openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    @TableField("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "心意卡名称")
    @TableField("name")
    private String name;
    
    @ApiModelProperty(value = "购买人openid")
    @TableField("consumer_openid")
    private String consumerOpenid;
    
    @ApiModelProperty(value = "转赠人openid")
    @TableField("give_openid")
    private String giveOpenid;
    
    @ApiModelProperty(value = "接受人openid")
    @TableField("receive_openid")
    private String receiveOpenid;

    @ApiModelProperty(value = "当前可用金额")
    @TableField("balance")
    private Float balance;


    @ApiModelProperty(value = "渠道(自己购买，好友转赠)")
    @TableField("channel")
    private String channel;

    @ApiModelProperty(value = "获取时间")
    @TableField("fetch_time")
    private Date fetchTime;

    @ApiModelProperty(value = "转赠时间")
    @TableField("give_time")
    private Date giveTime;

    @ApiModelProperty(value = "状态(0未激活 ，1已激活 ,2转赠中,3转赠失败，4转赠成功 ，5退款，6、领取 ，7、接受 )")
    @TableField("state")
    private Integer state;
    
    @ApiModelProperty(value = "心意卡卡面id")
    @TableField("hCard_map_id")
    private Long hCardMapId;
    
    @ApiModelProperty(value = "心意卡卡面图url")
    @TableField("img")
    private String img;
    
    @ApiModelProperty(value = "心意卡转赠次数限制")
    @TableField("gift_restriction_count")
    private Integer giftRestrictionCount;
    
    @ApiModelProperty(value = "心意卡定时任务id")
    @TableField("schedule_id")
    private Long scheduleId;
    
    @ApiModelProperty(value = "心意卡类型名称")
    @TableField("hCard_type_name")
    private String hCardTypeName;
    
    @ApiModelProperty(value = "心意卡价格id")
    @TableField("hCard_price_id")
    private Long hCardPriceId;
    
    @ApiModelProperty(value = "心意卡转赠记录id")
    @TableField("hCard_give_id")
    private String hCardGiveTd;

    @ApiModelProperty(value = "是否需要实体卡（0否 ，1是）")
    @TableField("entity_card")
    private String entityCard;

	public Long getHcardTypeId() {
		return hcardTypeId;
	}

	public void setHcardTypeId(Long hcardTypeId) {
		this.hcardTypeId = hcardTypeId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConsumerOpenid() {
		return consumerOpenid;
	}

	public void setConsumerOpenid(String consumerOpenid) {
		this.consumerOpenid = consumerOpenid;
	}

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

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(Date fetchTime) {
		this.fetchTime = fetchTime;
	}

	public Date getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(Date giveTime) {
		this.giveTime = giveTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long gethCardMapId() {
		return hCardMapId;
	}

	public void sethCardMapId(Long hCardMapId) {
		this.hCardMapId = hCardMapId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getGiftRestrictionCount() {
		return giftRestrictionCount;
	}

	public void setGiftRestrictionCount(Integer giftRestrictionCount) {
		this.giftRestrictionCount = giftRestrictionCount;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String gethCardTypeName() {
		return hCardTypeName;
	}

	public void sethCardTypeName(String hCardTypeName) {
		this.hCardTypeName = hCardTypeName;
	}

	public Long gethCardPriceId() {
		return hCardPriceId;
	}

	public void sethCardPriceId(Long hCardPriceId) {
		this.hCardPriceId = hCardPriceId;
	}

	public String gethCardGiveTd() {
		return hCardGiveTd;
	}

	public void sethCardGiveTd(String hCardGiveTd) {
		this.hCardGiveTd = hCardGiveTd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
