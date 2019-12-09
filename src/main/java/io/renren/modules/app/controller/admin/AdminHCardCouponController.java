package io.renren.modules.app.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.LayerMsg;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.common.utils.FileUpload;
import io.renren.modules.app.model.po.HCardCouponPO;
import io.renren.modules.app.model.vo.HCardCouponVO;
import io.renren.modules.app.service.AdminHCardCouponService;
import io.renren.common.utils.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 心意卡优惠券 前端控制器
 * </p>
 *
 * @author cth
 * @since 2019-10-19
 */
@RestController
@RequestMapping("/api/admin/hCardCoupon")
public class AdminHCardCouponController extends BaseController {
	@Autowired
	AdminHCardCouponService couponService;
	
	/**
	 * 
	 * @Title: list   
	 * @Description: 查询  
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月12日:下午7:50:08
	 * @throws
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public LayerMsg list(@RequestBody HCardCouponVO couponVO ){
		int pageNo=couponVO.getPageNo();
		int pageSize=couponVO.getPageSize();
		PageHelper.startPage(pageNo, pageSize);
		List<HCardCouponPO> list=couponService.list();
		PageInfo pageInfo = new PageInfo(list, pageSize);
        return LayerMsg.success(pageInfo.getTotal(), pageInfo.getList());
	}
	
	
	/**
	 * 
	 * @Title: add   
	 * @Description: 新增 
	 * @param: @param hCardTypePO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:25:57
	 * @throws
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> add(@RequestBody HCardCouponPO couponPO){
		
		boolean flag=couponService.save(couponPO);
		return flag?Result.success():Result.fail("新增失败！");
	}
	
	/**
	 * 
	 * @Title: add   
	 * @Description: 修改
	 * @param: @param hCardTypePO
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:25:57
	 * @throws
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result<?> update(@RequestBody HCardCouponPO couponPO){
		
		boolean flag=couponService.updateById(couponPO);
		return flag?Result.success():Result.fail("修改失败！");
	}
	
	/**
	 * 
	 * @Title: delete   
	 * @Description: 删除   
	 * @param: @param id
	 * @param: @return      
	 * @return: Result<?>      
	 * @date: 2019年10月13日:下午3:29:17
	 * @throws
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Result<?> delete(@RequestParam(value = "id") Long id){
		
		boolean flag=couponService.removeById(id);
		return flag?Result.success():Result.fail("删除失败！");
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
            String imgUrl = FileUpload.fileOne(file, StaticUtil.SAVE_HCARD_COUPON_DIR, StaticUtil.FILE_TYPE);
            return Result.success(0, imgUrl);
        }
		
	}
}
