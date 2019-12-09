package io.renren.modules.app.model.vo;

import lombok.Data;

/**   
 * @ClassName:  OpenidVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年11月19日 下午6:10:48   
 */
@Data
public class OpenidVO {
	
	private String openid;
	private int pageNo;
	private int pageSize;
	
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
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
	

}
