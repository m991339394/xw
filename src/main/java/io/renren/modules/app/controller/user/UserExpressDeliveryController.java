package io.renren.modules.app.controller.user;


import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.po.ExpressDeliveryPO;
import io.renren.modules.app.service.UserExpressDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 快递单号 前端控制器
 * </p>
 *
 * @author jgl
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/api/user/expressDelivery")
public class UserExpressDeliveryController extends BaseController {
    @Autowired
    UserExpressDeliveryService expressDeliveryService;

    /**
     *
     * @Title: acceptFriendToMeHCard
     * @Description: 打开好友赠送给我的心意卡
     * @param: @param hCardId
     * @param: @return
     * @return: Result<?>
     * @date: 2019年10月5日:下午3:34:39
     * @throws
     */
    @RequestMapping(value = "/getExpressDeliveryByHCardId.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> getExpressDeliveryByHCardId(@RequestParam(value = "hCardId") Long hCardId){
        try {
            ExpressDeliveryPO expressDeliveryPO=expressDeliveryService.getExpressDeliveryByHCardId(hCardId);
            return Result.success(expressDeliveryPO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500,e.getMessage());
        }
    }

}
