package io.renren.modules.app.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.LayerMsg;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.HCardConsumeRecordPO;
import io.renren.modules.app.model.po.UserHCardBalanceLogPO;
import io.renren.modules.app.model.vo.HCardConsumeRecordVO;
import io.renren.modules.app.model.vo.OpenidVO;
import io.renren.modules.app.model.vo.UserHCardBalanceLogVO;
import io.renren.modules.app.service.UserUserHCardBalanceLogService;
import io.renren.common.utils.ToMap;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.Map.Entry;

/**
 * <p>
 * 用户余额/积分变动纪录表 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/api/user/userHCardBalanceLog")
public class UserUserHCardBalanceLogController extends BaseController {
	@Autowired
	UserUserHCardBalanceLogService balanceLogServcice;
	
	/**
	 * 
	 * @Title: getUserHCardBalanceLog   
	 * @Description: 消费记录（心意卡钱包）
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月4日:下午4:53:31
	 * @throws
	 */
	@RequestMapping(value = "/getUserHCardBalanceLog.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public LayerMsg getUserHCardBalanceLog(UserHCardBalanceLogVO hCardBalanceLogVO){
		try {
			int pageNo=hCardBalanceLogVO.getPageNo();
			int pageSize=hCardBalanceLogVO.getPageSize();
			PageHelper.startPage(pageNo, pageSize);
			QueryWrapper<UserHCardBalanceLogPO> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("openid" ,hCardBalanceLogVO.getOpenid());
			queryWrapper.orderByDesc("useTime");
			List<UserHCardBalanceLogPO> list=balanceLogServcice.list(queryWrapper);
			PageInfo pageInfo = new PageInfo(list, pageSize);
			return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
	/**
	 * 
	 * @Title: refundRecord   
	 * @Description: 退款记录(心意卡钱包)
	 * @param: @param hCardBalanceLogPO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年11月14日:下午9:18:47
	 * @throws
	 */
	@RequestMapping(value = "/refundRecord.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg refundRecord(OpenidVO openidVO){
		
		try {
			int pageNo=openidVO.getPageNo();
			int pageSize=openidVO.getPageSize();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("openid", openidVO.getOpenid());
			map.put("useStatus", 3);
			List<UserHCardBalanceLogPO> list=balanceLogServcice.getUserHCardBalanceLogByUseStatus(map);
			PageInfo pageInfo = new PageInfo(list, pageSize);
			return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
	
	
public QueryWrapper<HCardConsumeRecordPO> getQueryWrapper(HCardConsumeRecordVO consumeRecordVO) {
		
		Map<String ,String> map= ToMap.obj2Map(consumeRecordVO);
		map.remove("pageNo");
		map.remove("pageSize");
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  //map.entrySet()得到的是set集合，可以使用迭代器遍历
		List<String> key= new ArrayList<>();
		List<Object> list=new ArrayList<>();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			System.out.println("key值："+entry.getKey()+" value值："+entry.getValue());
			key.add(entry.getKey());
			list.add(entry.getValue());
		}
		
		if(list.size()==0){
			new QueryWrapper<T>();
		}
		
		Map<String ,String> fieldName=ToMap.getProxyPojoValue(consumeRecordVO, key);
		QueryWrapper<HCardConsumeRecordPO> queryWrapper=new QueryWrapper<>();
		Iterator<Entry<String, String>> tableField= fieldName.entrySet().iterator();
		int i=0;
		while(tableField.hasNext()){
			Entry<String, String> entry = tableField.next();
			if("consume_time".equals(entry.getValue())){
				queryWrapper.gt(entry.getValue(),list.get(i++));
			}else {
				queryWrapper.eq(entry.getValue(),list.get(i++));
			}
		}
		
		return queryWrapper;
	}
}
