package io.renren.modules.app.controller.user;

import io.renren.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description Action
 * @Author
 * @Date
 * @Version 1.0
 **/

@Controller
@RequestMapping("api/user/test")
public class TestController  {

    private Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @RequestMapping(value = "/testGet.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result<?> testGet() {
    	Map<String, Object> relMap = new HashMap<String, Object>();
    	try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.fail(500,e.getMessage());
		}
    	return Result.success(relMap);
	}
    
    @RequestMapping(value = "/testPost.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HashMap<Object, Object> testPost(HttpServletRequest request) throws Exception {
        HashMap<Object, Object> relmap = new HashMap<Object, Object>();
        try {
            String postParameter = request.getParameter("postParameter");
            String name = request.getParameter("name");
            
            System.out.println(postParameter);
            System.out.println(name);
            
            if (null == postParameter || "".equals(postParameter)) {
                relmap.put("code", 0);
                relmap.put("msg", "postParameter参数为空");
                return relmap;
            }
            relmap.put("msg", "接口正常");
            relmap.put("data", postParameter);
            return relmap;
        } catch (Exception e) {
            e.printStackTrace();
            relmap.put("code", 500);
            relmap.put("msg", "接口异常");
            return relmap;
        }
    }
    
}