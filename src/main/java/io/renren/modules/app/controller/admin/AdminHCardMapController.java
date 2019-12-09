package io.renren.modules.app.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.LayerMsg;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.common.utils.FileUpload;
import io.renren.common.utils.StringUtils;
import io.renren.modules.app.model.po.HCardMapPO;
import io.renren.modules.app.model.po.HCardPricePO;
import io.renren.modules.app.service.AdminHCardMapService;
import io.renren.modules.app.service.AdminHCardPriceService;
import io.renren.common.utils.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 心意卡卡面图 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/admin/hCardMap")
public class AdminHCardMapController extends BaseController {
	@Autowired
	AdminHCardMapService adminHCardMapService;
	@Autowired
	AdminHCardPriceService adminHCardPriceService;
	
	/**
	 * 
	 * @Title: getHCardMapByHCardTypeId   
	 * @Description: 根据卡类型查询卡面图   
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年9月28日:下午5:11:41
	 * @throws
	 */
	@RequestMapping(value = "/getHCardMapByHCardTypeId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public LayerMsg getHCardMapByHCardTypeId(@RequestParam(value = "pageNo") int pageNo
											, @RequestParam(value = "pageSize") int pageSize
											, @RequestParam(value = "hCardTypeId" ,required = false) String hCardTypeId
											, @RequestParam(value = "name" ,required = false) String name){
    	try {
			PageHelper.startPage(pageNo, pageSize);
    		QueryWrapper<HCardMapPO> queryWrapper=new QueryWrapper<>();
    		if(StringUtils.isNotEmpty(hCardTypeId)) {
    			queryWrapper.eq("hCard_type_id", hCardTypeId);
    		}
    		if(StringUtils.isNotEmpty(name)) {
    			queryWrapper.likeRight("name", name);
    		}
    		List<HCardMapPO> list=adminHCardMapService.list(queryWrapper);
    		PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
	
	/**
	 * 
	 * @Title: getHCardByHCardTypeId   
	 * @Description: 根据卡类型查询卡面图和价格
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年9月28日:下午5:12:41
	 * @throws
	 */
	@RequestMapping(value = "/getHCardByHCardTypeId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardByHCardTypeId(@RequestParam(value = "hCardTypeId") Long hCardTypeId){
		Map<String, Object> relMap = new HashMap<String, Object>();
    	try {
    		QueryWrapper queryWrapper=new QueryWrapper();
    		queryWrapper.eq("hCard_type_id", hCardTypeId);
    		List<HCardMapPO> hCardMapPOs=adminHCardMapService.list(queryWrapper);
    		List<HCardPricePO> hCardPricePOs=adminHCardPriceService.list(queryWrapper);
    		relMap.put("hCardMapPOs", hCardMapPOs);
    		relMap.put("hCardPricePOs", hCardPricePOs);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
    	return Result.success(relMap);
	}
	
	/**
	 * 
	 * @Title: getHCardByHCardTypeId   
	 * @Description: 新增卡面图   
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午3:12:45
	 * @throws
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> add(@RequestBody HCardMapPO hCardMapPO){
		
		boolean flag=adminHCardMapService.save(hCardMapPO);
		return flag?Result.success():Result.fail();
	}
	
	/**
	 * 
	 * @Title: getHCardByHCardTypeId   
	 * @Description: 修改卡面图
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午3:12:45
	 * @throws
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> update(@RequestBody HCardMapPO hCardMapPO){
		
		boolean flag=adminHCardMapService.updateById(hCardMapPO);
		return flag?Result.success():Result.fail();
	}
	
	/**
	 * 
	 * @Title: getHCardByHCardTypeId   
	 * @Description: 删除卡面图
	 * @param: @param hCardTypeId
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午3:12:45
	 * @throws
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> delete(@RequestParam(value = "id") Long id){
		
		boolean flag=adminHCardMapService.removeById(id);
		return flag?Result.success():Result.fail();
	}
	
	/**
	 * 
	 * @Title: update   
	 * @Description: 心意卡卡面图图片上传   
	 * @param: @param hCardTypePO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月12日:上午11:21:56
	 * @throws
	 */
	@RequestMapping(value = "/imgUpload.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> imgUpload(@RequestParam(value = "file", required = false) MultipartFile file){
		
		if (file == null) {
            return Result.fail("图片上传失败！");
        } else {
            String imgUrl = FileUpload.fileOne(file, StaticUtil.SAVE_HCARD_MAP_DIR, StaticUtil.FILE_TYPE);
            return Result.success(0, imgUrl);
        }
		
	}
	

}
