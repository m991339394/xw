package io.renren.modules.app.model.vo;

import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import io.swagger.annotations.ApiModelProperty;

/**   
 * @ClassName:  UserHCardBalanceLogVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年11月19日 上午11:23:20   
 */
public class UserHCardBalanceLogVO extends UserHCardBalanceLogPO {
	
	private int pageNo;
	private int pageSize;
	
	@ApiModelProperty(value = "查询开始时间")
    private String startTime;
	
	@ApiModelProperty(value = "查询结束时间")
    private String endTime;
	
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
	
	
	

}
