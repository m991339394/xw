package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.Result;
import io.renren.modules.app.model.form.LoginForm;
import io.renren.modules.app.model.po.UserPO;
import io.renren.modules.app.model.vo.UserVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jgl
 * @since 2019-10-05
 */
public interface UserUserService extends IService<UserPO> {
	
	Result<?> login(LoginForm loginForm);
	UserVO findByOpenId(String openid);
}
