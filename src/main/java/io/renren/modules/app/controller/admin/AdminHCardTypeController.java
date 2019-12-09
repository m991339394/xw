package io.renren.modules.app.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.Constants.HCardTypeConstants;
import io.renren.common.LayerMsg;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.common.utils.FileUpload;
import io.renren.common.utils.StringUtils;
import io.renren.modules.app.model.po.HCardTypePO;
import io.renren.modules.app.service.AdminHCardTypeService;
import io.renren.common.utils.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 心意卡类型 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/admin/hCardType")
public class AdminHCardTypeController extends BaseController {
	@Autowired
	AdminHCardTypeService adminHCardTypeService;
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 查询卡类型   （已上架的）
	 * @param: @return      
	 * @return: Result<?>      
	 * @throws
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public LayerMsg list(@RequestParam(value = "pageNo" ,defaultValue = "1") Integer pageNo
						, @RequestParam(value = "pageSize" ,defaultValue = "10") Integer pageSize
						, @RequestParam(value = "name" ,required = false) String name){
		try {
			PageHelper.startPage(pageNo, pageSize);
			QueryWrapper<HCardTypePO> qWrapper=new QueryWrapper<HCardTypePO>();
			qWrapper.eq("isDel", HCardTypeConstants.NOT_DEL);
			if(StringUtils.isNotEmpty(name)){
				qWrapper.like("name", name);
			}
	    	List<HCardTypePO> list=adminHCardTypeService.list(qWrapper);
			PageInfo pageInfo = new PageInfo(list, pageSize);
	        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
		
	}
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 查询卡id（已上架的）
	 * @param: @return      
	 * @return: Result<?>      
	 * @throws
	 */
	@RequestMapping(value = "/getHCardTypeIds.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> getHCardTypeIds(){
		try {
			QueryWrapper<HCardTypePO> qWrapper=new QueryWrapper<HCardTypePO>();
			qWrapper.eq("isDel", HCardTypeConstants.NOT_DEL);
			List<HCardTypePO> list=adminHCardTypeService.list(qWrapper);
			return Result.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail();
		}
		
	}
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 新增心意卡类型   
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午2:17:42
	 * @throws
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> add(@RequestBody HCardTypePO hCardTypePO){
		
		boolean flag=adminHCardTypeService.save(hCardTypePO);
		return flag?Result.success():Result.fail("新增心意卡类型失败！");
	}
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 修改心意卡类型   
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午2:17:42
	 * @throws
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> update(@RequestBody HCardTypePO hCardTypePO){
		
		boolean flag=adminHCardTypeService.updateById(hCardTypePO);
		return flag?Result.success():Result.fail("修改心意卡类型失败！");
	}
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 删除心意卡类型   (逻辑删除)
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午2:17:42
	 * @throws
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> delete(@RequestParam(value = "id") Long id){
		
		HCardTypePO hCardTypePO=adminHCardTypeService.getById(id);
		if(hCardTypePO.getUpperShelf()==HCardTypeConstants.UPPER_SHELF){
			return Result.fail("请先下架，在删除！");
		}
		hCardTypePO.setId(id);
		hCardTypePO.setIsDel(HCardTypeConstants.DEL);
		boolean flag=adminHCardTypeService.updateById(hCardTypePO);
		return flag?Result.success():Result.fail("删除心意卡类型失败！");
	}
	
	
	/**
	 * 
	 * @Title: update   
	 * @Description: 图片上传   
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
            String imgUrl = FileUpload.fileOne(file, StaticUtil.SAVE_HCARD_TYPE_DIR, StaticUtil.FILE_TYPE);
            return Result.success(0, imgUrl);
        }
		
	}
	
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 心意卡下架 
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月8日:下午2:17:42
	 * @throws
	 */
	@RequestMapping(value = "/upperSheftAndLowerSheft.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> upperSheftAndLowerSheft(HCardTypePO hCardTypePO){
		
		Result result=adminHCardTypeService.upperSheftAndLowerSheft(hCardTypePO);
		return result;
	}
	
	
	
}
