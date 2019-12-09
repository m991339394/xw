package io.renren.modules.app.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.LayerMsg;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.HCardConsumeRecordPO;
import io.renren.modules.app.model.vo.HCardConsumeRecordVO;
import io.renren.modules.app.service.AdminHCardConsumeRecordService;
import io.renren.common.utils.ToMap;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * 心意卡消费记录 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/admin/hCardConsumeRecord")
public class AdminHCardConsumeRecordController extends BaseController {
	@Autowired
	AdminHCardConsumeRecordService consumeRecordService;
	
	/**
	 * 
	 * @Title: getConsumeRecord   
	 * @Description: 心意卡消费记录
	 * @param: @param openid
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月4日:下午4:53:31
	 * @throws
	 */
	@RequestMapping(value = "/getConsumeRecord.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg getConsumeRecord(@RequestBody HCardConsumeRecordVO consumeRecordVO){
		
		try {
			int pageNo=consumeRecordVO.getPageNo();
			int pageSize=consumeRecordVO.getPageSize();
			PageHelper.startPage(pageNo, pageSize);
			QueryWrapper<HCardConsumeRecordPO> queryWrapper=getQueryWrapper(consumeRecordVO);
			List<HCardConsumeRecordPO> list=consumeRecordService.list(queryWrapper);
			PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
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
