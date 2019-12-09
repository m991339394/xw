package io.renren.modules.app.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**   
 * @ClassName:  UserHCardVO   
 * @Description:    
 * @author: jgl
 * @date:   2019年10月17日 下午8:32:39   
 */
@Data
public class UserHCardForm{
	
	@ApiModelProperty(value = "查询开始时间")
	private String startTime;
	 
	@ApiModelProperty(value = "查询结束时间")
	private String endTime;
	
    @ApiModelProperty(value = "订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "金额")
    private Float balance;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    
    @ApiModelProperty(value = "电话")
    private String mobile;
    
    @ApiModelProperty(value = "心意卡名称")
    private String name;
    
    @ApiModelProperty(value = "第几页")
	private int pageNo;
	
    @ApiModelProperty(value = "查询数据的条数")
	private int pageSize;
    
    @ApiModelProperty(value = "类型（1 、购卡记录  2、消费记录  3、转赠记录  4、激活记录）")
    private int type;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
