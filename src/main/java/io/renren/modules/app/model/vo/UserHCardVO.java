package io.renren.modules.app.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**   
 * @ClassName:  UserHCardVO   
 * @Description:    
 * @author: jgl
 * @date:   2019年10月17日 下午8:32:39   
 */
@Data
public class UserHCardVO{

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

	@ApiModelProperty(value = "面额")
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

	private String nickName;
	private String realName;
	private String mobile;
	private String stateString;

}
