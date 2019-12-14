package io.renren.modules.app.controller.admin;


import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.common.utils.FileUpload;
import io.renren.common.utils.StaticUtil;
import io.renren.modules.app.model.po.HCardTypePO;
import io.renren.modules.app.model.po.LePO;
import io.renren.modules.app.service.AdminHCardTypeService;
import io.renren.modules.app.service.AdminUserLeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-12-13
 */
@RestController
@RequestMapping("/api/admin/Article")
public class AdminUserLeController extends BaseController {

    @Autowired
    AdminUserLeService adminUserLeService;

    @Autowired
    AdminHCardTypeService typeService;

    /**
     * 查找全部文章
     * @return
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> list(){

        List<LePO> list=adminUserLeService.list();
        return Result.success(list);
    }


    /**
     * 新增文章
     * @param lePO
     * @return
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<?> add(@RequestBody LePO lePO){

        if(lePO.getId()!=null){
            HCardTypePO hCardTypePO = typeService.getById(lePO.getId());
            lePO.setTitle(lePO.getTitle());
            lePO.setCreateTime(new Date());
        }
        lePO.setCreateTime(new Date());
        boolean flag=adminUserLeService.save(lePO);
        return flag?Result.success():Result.fail("新增失败！");
    }


    /**
     * 修改文章
     * @param lePO
     * @return
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<?> update(@RequestBody LePO lePO){

        if(lePO.getId()!=null){
            HCardTypePO hCardTypePO = typeService.getById(lePO.getId());
            lePO.setTitle(lePO.getTitle());
            lePO.setUpdateTime(new Date());
        }

        boolean flag=adminUserLeService.updateById(lePO);
        return flag?Result.success():Result.fail("修改失败！");
    }

    /**
     /*
     * 删除文章
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> delete(@RequestParam(value = "id") Long id){

        boolean flag=adminUserLeService.removeById(id);
        return flag?Result.success():Result.fail("删除失败！");
    }

    /**
     *
     * @Title: update
     * @Description: 轮播图上传
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
