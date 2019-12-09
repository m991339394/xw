package io.renren.modules.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.Result;
import io.renren.common.config.PayConfig;
import io.renren.common.exception.RRException;
import io.renren.common.utils.HttpRequest;
import io.renren.modules.app.dao.UserUserDao;
import io.renren.modules.app.model.form.LoginForm;
import io.renren.modules.app.model.po.UserHCardPursePO;
import io.renren.modules.app.model.po.UserPO;
import io.renren.modules.app.model.vo.UserVO;
import io.renren.modules.app.service.UserUserHCardPurseService;
import io.renren.modules.app.service.UserUserService;
import io.renren.common.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-10-05
 */
@Service
public class UserUserServiceImpl extends ServiceImpl<UserUserDao, UserPO> implements UserUserService {
	@Autowired
	UserUserDao userDao;
	@Autowired
	UserUserHCardPurseService hCardPurseService;

	@Override
	public UserVO findByOpenId(String openid) {
		// TODO Auto-generated method stub
		return userDao.findByOpenId(openid);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Result<?> login(LoginForm loginForm) {
		String code=loginForm.getCode();
		String nickName=loginForm.getNickName();
		String faceIcon=loginForm.getFaceIcon();
		String type=loginForm.getType();

		 if (StringUtils.isBlank(code)) {//判断code是否为空
	            return Result.fail(1, "code为空");
	        }

		String wxspAppid;
		String wxspSecret;
		if("2".equals(type)){
				wxspAppid = PayConfig.APPID2;
				wxspSecret = PayConfig.APP_SECRET2;
		}else{
			wxspAppid = PayConfig.APPID;
			wxspSecret = PayConfig.APP_SECRET;
		}

		String grant_type = "authorization_code";
		String param = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
		String sr = HttpRequest.sendPost("https://api.weixin.qq.com/sns/jscode2session", param);//向指定 URL 发送POST方法的请求

		if (sr != null && sr.length() > 0) {
	            JSONObject jsonObject = JSONObject.parseObject(sr);
	            if (jsonObject.get("openid") != null) {
	                String openid = jsonObject.get("openid").toString();
	                String session_key = jsonObject.getString("session_key");
	                    UserVO user = userDao.findByOpenId(openid);
	                    //如果是老用户
	                    DozerBeanMapper dozerBeanMapper=new DozerBeanMapper();
	                    if (user != null) {
	                    	UserVO userVO=dozerBeanMapper.map(user, UserVO.class);
	                    	userVO.setOpenId(openid);
	                        return Result.success(userVO);
	                    } else {
	                    	// 新用户
	                        UserPO user2 = new UserPO();
	                        user2.setOpenid(openid);
	                        user2.setCreateTime(TimeUtil.getTimestamp());
	                        user2.setUserId(openid);
	                        user2.setNickName(nickName);
	                        user2.setFaceIcon(faceIcon);
	                        user2.setCreateTime(TimeUtil.getTimestamp());
	                        int num = userDao.insert(user2);
	                        UserVO userVO=dozerBeanMapper.map(user2, UserVO.class);
	                        userVO.setOpenId(openid);
	                        if(num==1) {
	                        	// 创建心意卡钱包
	                        	UserHCardPursePO userHCardPursePO=new UserHCardPursePO();
	                        	userHCardPursePO.setOpenid(openid);
	                        	userHCardPursePO.setBalance(0f);
	                        	userHCardPursePO.setCreateTime(new Date());
	                        	boolean flag=hCardPurseService.save(userHCardPursePO);
	                        	if(!flag) {
	                        		throw new RRException("创建礼品卡卡钱包" + "异常,"+" openid ="+openid ,new Throwable());
	                        	}
	                        }
	                        return Result.success(userVO);
	                    }
	            } else {
	                return Result.fail("openId空错误");
	            }
	        } else {
	            return Result.fail("请求错误");
	        }
	}
	
}
