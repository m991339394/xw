package io.renren.common.Constants;

/**   
 * @ClassName:  HCardConstants   
 * @Description:TODO(用户心意卡，常量类)   
 * @author: jgl
 * @date:   2019年10月5日 下午3:09:15   
 */
public class HCardConstants {

    public static final String FAIL = "发生异常，请联系客服"; // 失败
    public static final String SUCCESS = "成功"; // 成功
    
    
    public static final Integer STATE_NOT_ACTIVE = 0; // 未激活
    public static final Integer STATE_ACTIVE = 1; // 已激活
    public static final Integer STATE_IN_DONATION = 2; // 转赠中
    public static final Integer STATE_FAIL_DONATION = 3; // 转赠失败
    public static final Integer STATE_SUCCESS_DONATION = 6; // 转赠成功
    public static final Integer STATE_REFUND = 5; // 退款成功
    public static final Integer STATE_SUCCESS_RECEIVED = 6; // 领取红包
    public static final Integer STATE_SUCCESS_OPEN = 7; // 打开红包

    public static final String STATE_NOT_ACTIVE_STRING = "未激活"; // 未激活
    public static final String STATE_ACTIVE_STRING = "已激活"; // 已激活
    public static final String STATE_IN_DONATION_STRING = "转赠中"; // 转赠中
    public static final String STATE_FAIL_DONATION_STRING = "转赠失败"; // 转赠失败
    public static final String STATE_SUCCESS_DONATION_STRING = "转赠成功"; // 转赠成功
    public static final String STATE_REFUND_STRING = "退款成功"; // 退款成功
    public static final String STATE_SUCCESS_RECEIVED_STRING = "领取红包"; // 领取红包
    public static final String STATE_SUCCESS_OPEN_STRING = "打开红包"; // 打开红包

    public static final Integer NOT_RECEIVE = 0; // 未领取
    public static final Integer RECEIVED = 1; // 已领取
    public static final Integer RETURN_DONATION = 2; // 转赠退回
    
    public static final String CHANNEL_SELF_BUY = "自己购买"; // 自己购买
    public static final String CHANNEL_FROM_FRIENDS = "好友转赠"; // 好友转赠
    
    public static final Integer ORDER_UNPAID = 0; // 订单未支付
    public static final Integer ORDER_PAID = 1; // 订单已付款
    public static final Integer ORDER_CANCEL = 2; // 取消订单
    
    public static final Integer LOWER_SHELF = 0; // 下架
    public static final Integer UPPER_SHELF = 1; // 上架
    
    public static final Integer NOT_DEL = 0; // 未删除
    public static final Integer DEL = 1; // 删除
    
}
