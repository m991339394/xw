package io.renren.modules.app.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.LayerMsg;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.common.utils.FileUpload;
import io.renren.common.utils.StaticUtil;
import io.renren.modules.app.model.po.HCardInsideSwiperPO;
import io.renren.modules.app.model.po.HCardTypePO;
import io.renren.modules.app.service.AdminHCardInsideSwiperService;
import io.renren.modules.app.service.AdminHCardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 心意卡轮播图 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-13
 */
@RestController
@RequestMapping("/api/admin/hCardInsideSwiper")
public class AdminHCardInsideSwiperController extends BaseController {
	@Autowired
	AdminHCardInsideSwiperService insideSwiperService;
	@Autowired
	AdminHCardTypeService typeService;
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 查询商城内轮播图  
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月12日:下午7:50:08
	 * @throws
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public LayerMsg list(@RequestParam(value = "pageNo" ,defaultValue = "1") Integer pageNo
						,@RequestParam(value = "pageSize" ,defaultValue = "10") Integer pageSize){
		try {
			PageHelper.startPage(pageNo, pageSize);
			List<HCardInsideSwiperPO> list=insideSwiperService.list();
			PageInfo pageInfo = new PageInfo(list, pageSize);
			return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return LayerMsg.fail();
		}
	}
	
	
	/**
	 * 
	 * @Title: add   
	 * @Description: 新增首页轮播图   
	 * @param: @param hCardTypePO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:25:57
	 * @throws
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> add(@RequestBody HCardInsideSwiperPO hCardInsideSwiperPO){
		
		if(hCardInsideSwiperPO.getHcardTypeId()!=null){
			HCardTypePO hCardTypePO = typeService.getById(hCardInsideSwiperPO.getHcardTypeId());
			hCardInsideSwiperPO.setName(hCardTypePO.getName());
			hCardInsideSwiperPO.setCreatedTime(new Date());
		}
		boolean flag=insideSwiperService.save(hCardInsideSwiperPO);
		return flag?Result.success():Result.fail("新增失败！");
	}
	
	/**
	 * 
	 * @Title: add   
	 * @Description: 修改首页轮播图   
	 * @param: @param hCardTypePO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:25:57
	 * @throws
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> update(@RequestBody HCardInsideSwiperPO hCardInsideSwiperPO){
		
		if(hCardInsideSwiperPO.getHcardTypeId()!=null){
			HCardTypePO hCardTypePO = typeService.getById(hCardInsideSwiperPO.getHcardTypeId());
			hCardInsideSwiperPO.setName(hCardTypePO.getName());
			hCardInsideSwiperPO.setUpdatedTime(new Date());
		}
		boolean flag=insideSwiperService.updateById(hCardInsideSwiperPO);
		return flag?Result.success():Result.fail("修改失败！");
	}
	
	/**
	 * 
	 * @Title: delete   
	 * @Description: 删除首页轮播图   
	 * @param: @param id
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:29:17
	 * @throws
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> delete(@RequestParam(value = "id") Long id){
		
		boolean flag=insideSwiperService.removeById(id);
		return flag?Result.success():Result.fail("删除失败！");
	}
	
	
	/**
	 * 
	 * @Title: update   
	 * @Description: 商城内轮播图上传   
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
            String imgUrl = FileUpload.fileOne(file, StaticUtil.SAVE_HCARD_INSIDE_DIR, StaticUtil.FILE_TYPE);
            return Result.success(0, imgUrl);
        }
		
	}
}
