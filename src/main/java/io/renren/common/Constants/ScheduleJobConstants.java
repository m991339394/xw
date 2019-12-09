package io.renren.common.Constants;

/**   
 * @ClassName:  HCardConstants   
 * @Description:TODO(用户心意卡，常量类)   
 * @author: jgl
 * @date:   2019年10月5日 下午3:09:15   
 */
public class ScheduleJobConstants {
	
	public static final String SEPARATOR = "/"; // 退卡任务spring bean的名称

    public static final String RETURN_HCARD_BEAN_NAME = "returnHCard"; // 退卡任务spring bean的名称
    public static final String RETURN_HCARD_REMARK = "退卡任务"; // 退卡任务备注
    public static final Long RETURN_HCARD_DELAY_SECONDS = 5*60*1000L; // 退卡任务延时时间（单位：毫秒）
    public static final String RETURN_HCARD_PARAMS = "return"; // 退卡任务参数前缀
    
    public static final String CANCEL_ORDER_BEAN_NAME = "cancelHCardOrder"; // 关闭订单任务spring bean的名称
    public static final String CANCEL_ORDER_REMARK = "关闭订单任务"; // 关闭订单任务备注
    public static final Long CANCEL_ORDER_DELAY_SECONDS = 1*60*1000L; // 关闭订单任务延时时间（单位：毫秒）
    public static final String CANCEL_ORDER_PARAMS = "order"; // 关闭订单任务参数前缀
    
    
}
