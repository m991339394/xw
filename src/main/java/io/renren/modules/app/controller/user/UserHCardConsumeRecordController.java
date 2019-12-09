package io.renren.modules.app.controller.user;//package io.renren.modules.app.controller.user;
//
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//
//import io.renren.modules.app.common.Result;
//import io.renren.modules.app.model.po.HCardConsumeRecordPO;
//import io.renren.modules.app.model.po.UserHCardPO;
//import io.renren.modules.app.service.UserHCardConsumeRecordService;
//
///**
// * <p>
// * 心意卡消费记录 前端控制器
// * </p>
// *
// * @author jgl
// * @since 2019-10-04
// */
//@RestController
//@RequestMapping("/api/user/hCardConsumeRecord")
//public class UserHCardConsumeRecordController {
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//	@Autowired
//	UserHCardConsumeRecordService recordService;
//
//	/**
//	 * 
//	 * @Title: getConsumeRecord   
//	 * @Description: 心意卡消费记录 
//	 * @param: @param openid
//	 * @param: @return      
//	 * @return: Result<?>      
//	 * @date: 2019年10月4日:下午4:53:31
//	 * @throws
//	 */
//	@RequestMapping(value = "/getHCardConsumeRecord.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//	public Result<?> getHCardConsumeRecord(@RequestParam(value = "hCardId") String hCardId){
//		try {
//			QueryWrapper<HCardConsumeRecordPO> queryWrapper=new QueryWrapper<>();
//			queryWrapper.eq("h_card_id" ,hCardId);
//			List<HCardConsumeRecordPO> list=recordService.list(queryWrapper);
//			return new Result(list);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return Result.fail(500,e.getMessage());
//		}
//	}
//	
//	/**
//	 * 
//	 * @Title: getConsumeRecord   
//	 * @Description: 用户消费记录   
//	 * @param: @param openid
//	 * @param: @return      
//	 * @return: Result<?>      
//	 * @date: 2019年10月4日:下午4:53:31
//	 * @throws
//	 */
//	@RequestMapping(value = "/getUserConsumeRecord.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//	public Result<?> getUserConsumeRecord(@RequestParam(value = "openid") String openid){
//		try {
//			QueryWrapper<HCardConsumeRecordPO> queryWrapper=new QueryWrapper<>();
//			queryWrapper.eq("openid" ,openid);
//			List<HCardConsumeRecordPO> list=recordService.list(queryWrapper);
//			return new Result(list);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return Result.fail(500,e.getMessage());
//		}
//	}
//}
