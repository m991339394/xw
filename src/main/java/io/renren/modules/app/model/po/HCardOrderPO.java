package io.renren.modules.app.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户购买心意卡订单记录
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ggwl_HCard_order")
@ApiModel(value="HCardOrderPO对象", description="用户购买心意卡订单记录")
public class HCardOrderPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "心意卡类型id")
    @TableField("hCard_type_id")
    private Long hcardTypeId;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @ApiModelProperty(value = "订单号")
    @TableField("out_trade_no")
    private String outTradeNo;
    
    @ApiModelProperty(value = "用户openid")
    @TableField("openid")
    private String openid;
    
    @ApiModelProperty(value = "心意卡价格id 和 数量")
    @TableField("hCard_price_ids")
    private String hcardPriceIds;

    @ApiModelProperty(value = "设备号")
    @TableField("device_info")
    private String deviceInfo;

    @ApiModelProperty(value = "商品描述")
    @TableField("body")
    private String body;

    @ApiModelProperty(value = "商品详情")
    @TableField("detail")
    private String detail;

    @ApiModelProperty(value = "附加数据")
    @TableField("attach")
    private String attach;

    @ApiModelProperty(value = "终端ip")
    @TableField("spbill_create_ip")
    private String spbillCreateIp;

    @ApiModelProperty(value = "交易起始时间")
    @TableField("time_start")
    private Date timeStart;

    @ApiModelProperty(value = "交易结束时间")
    @TableField("time_expire")
    private Date timeExpire;

    @ApiModelProperty(value = "总金额")
    @TableField("total_fee")
    private Float totalFee;

    @ApiModelProperty(value = "交易类型")
    @TableField("trade_type")
    private String tradeType;

    @ApiModelProperty(value = "交易状态（0失败 ，1成功）")
    @TableField("state")
    private Integer state;
    
    @ApiModelProperty(value = "心意卡卡面id")
    @TableField("hCard_map_id")
    private Long hCardMapId;
    
    @ApiModelProperty(value = "心意卡卡面图url")
    @TableField("img")
    private String img;
    
    @ApiModelProperty(value = "心意卡卡面图url")
    @TableField("schedule_id")
    private Long scheduleId;
    
    @ApiModelProperty(value = "用户心意卡心意卡优惠卷id")
    @TableField("user_coupon_id")
    private Long userCouponId;

	public Long getHcardTypeId() {
		return hcardTypeId;
	}

	public void setHcardTypeId(Long hcardTypeId) {
		this.hcardTypeId = hcardTypeId;
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getHcardPriceIds() {
		return hcardPriceIds;
	}

	public void setHcardPriceIds(String hcardPriceIds) {
		this.hcardPriceIds = hcardPriceIds;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire;
	}

	public Float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
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

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getUserCouponId() {
		return userCouponId;
	}

	public void setUserCouponId(Long userCouponId) {
		this.userCouponId = userCouponId;
	}
	

}
