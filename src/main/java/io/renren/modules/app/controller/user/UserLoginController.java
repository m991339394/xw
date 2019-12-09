package io.renren.modules.app.controller.user;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.Result;
import io.renren.common.utils.BaseController;
import io.renren.modules.app.model.form.LoginForm;
import io.renren.modules.app.service.UserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* @Author 292541410
* @Description
**/

@RestController
@RequestMapping("/api/user/login")
public class UserLoginController extends BaseController {
    @Autowired
    private UserUserService userService;
    
    /**
     * 
     * @Title: login   
     * @Description: 小程序登录   
     * @param: @param code
     * @param: @return
     * @param: @throws Exception      
     * @return: Result<?>      
     * @date: 2019年10月5日:下午2:46:16
     * @throws
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<?> login(LoginForm loginForm) throws Exception {

       return userService.login(loginForm);
    }
    
    
    @RequestMapping(value = "/testLogin.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<?> testlogin(@RequestParam(value="jsCode" ,required = false)String jsCode ) throws Exception {
    	String data="{\"openId\":\"oevu84tSu1Bo9L60S2h0C3hhoFAA\",\"state\":1,\"userId\":59158,\"nickName\":\"莫凡DilBeg\",\"faceIcon\":\"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqr39K9sobLxY5YRWVVmSg4VoB8icg4D8AzIiajXKiaLTzYpXAC4yXe9ZibWNBfCEib6srsicm1rqic3QTNg/132\",\"realName\":null,\"gender\":1,\"mobile\":\"13669999415\",\"balance\":864.00,\"integralBalance\":1000.00,\"sessionId\":\"fe0cf5fdec894c139886ae1836064d56\"}";
    	Object object=JSONObject.parse(data);
    	return Result.success(object);
    }
    

}
