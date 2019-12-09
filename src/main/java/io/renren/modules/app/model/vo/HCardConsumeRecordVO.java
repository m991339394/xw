package io.renren.modules.app.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.renren.modules.app.model.po.HCardConsumeRecordPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**   
 * @ClassName:  HCardConsumeRecordVO   
 * @Description:    
 * @author: jgl
 * @date:   2019年10月17日 下午5:57:21   
 */
@Data
public class HCardConsumeRecordVO  extends HCardConsumeRecordPO {
	
	@ApiModelProperty(value = "用户openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "订单号")
    @TableField("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "心意卡编号")
    @TableId(value = "h_card_id", type = IdType.INPUT)
    private Long hCardId;

    @ApiModelProperty(value = "消费时间")
    @TableField("consume_time")
    private Date consumeTime;

    @ApiModelProperty(value = "心意卡名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "消费人")
    @TableField("consumer")
    private String consumer;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "消费金额")
    @TableField("money")
    private Float money;

    @ApiModelProperty(value = "商家")
    @TableField("business")
    private String business;
	
	private int pageNo;
	private int pageSize;
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNum(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	public Long gethCardId() {
		return hCardId;
	}
	public void sethCardId(Long hCardId) {
		this.hCardId = hCardId;
	}
	public Date getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	
}
