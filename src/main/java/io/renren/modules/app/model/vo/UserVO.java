package io.renren.modules.app.model.vo;

import io.renren.modules.app.model.po.UserPO;
import lombok.Data;

/**   
 * @ClassName:  UserDTO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年10月5日 上午11:29:42   
 */
@Data
public class UserVO extends UserPO {
	
	private Double balance;
	private Long integralBalance;
	private String openId;
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Long getIntegralBalance() {
		return integralBalance;
	}
	public void setIntegralBalance(Long integralBalance) {
		this.integralBalance = integralBalance;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	

}
