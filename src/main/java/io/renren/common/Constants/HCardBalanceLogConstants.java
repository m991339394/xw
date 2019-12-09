package io.renren.common.Constants;

/**   
 * @ClassName:  HCardConstants   
 * @Description:TODO(用户心意卡，常量类)   
 * @author: jgl
 * @date:   2019年10月5日 下午3:09:15   
 */
public class HCardBalanceLogConstants {
	
    public static final Integer USE_STATES_RECHARGE = 1; // 新增
    public static final Integer USE_STATES_CONSUME = 2; // 消耗
    public static final Integer USE_STATES_REFUND = 3; // 消耗
    
    public static final Integer SOURCE_TYPE_ACTIVE = 1; // 心意卡激活 
    public static final Integer SOURCE_TYPE_CONSUME = 2; // 购买商品
    public static final Integer SOURCE_TYPE_REFUND = 3; // 订单退款
    
    public static final Integer ORDER_TYPE_FINE_FOOD = 1; // 美食
    public static final Integer ORDER_TYPE_MTX_MOVIE = 2; // MTX电影
    public static final Integer ORDER_TYPE_TRAVEL = 3; // 旅游
    public static final Integer ORDER_TYPE_MOVIE_TICKET = 4; // 淘票票电影
    public static final Integer ORDER_TYPE_MOVIE_BAR = 5; // 影吧
    
}
