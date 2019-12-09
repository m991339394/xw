package io.renren.common.utils;

import java.io.File;

public class StaticUtil {


    public static final String STATIC_URL = "https://flowAdmin.834445.net/file";

	// 图片存放的根路径
//    public static final String STATIC_URL = "http://192.168.111.190:8088/pic";  // /home/centos/wenjian/   <==>   https://zonguo.xyz/filepath/

    public static final String SAVE_URL_LINUX = File.separator + "www" + File.separator + "wwwroot" + File.separator + "flowAdmin.834445.net"+File.separator + "file";
    
//    public static final String SAVE_URL_LINUX = "D:\\pic";

    public static final String FILE_TYPE = "jpeg,jpg,png";

    // 静态资源相关
    public static final String RESOURCE_URL = "/static/**";
    public static final String WEBJAR_URL = "/resource/**";
    public static final String TEMPLATES_RUL = "/templates/**";
    public static final String CLASSPATH = "classpath:/static/";

    // 登录拦截相关
    public static final String ADMINSESSION = "admin";
    public static final String ADMINLOGIN = "/login/page";
    public static final String ADMINLOGINURL = "/login/check";

    // 项目名
    public static final String PROJECTNAME = "/gaogwlbackend";
    

    // 心意卡类型图片存放地址
    public static final String SAVE_HCARD_TYPE_DIR = File.separator + "images" + File.separator +"type" + File.separator ;
    // 心意卡卡面图图片存放地址
    public static final String SAVE_HCARD_MAP_DIR = File.separator + "images" + File.separator + "map" + File.separator;
    // 心意卡首页轮播图存放地址
    public static final String SAVE_HCARD_HOME_DIR = File.separator + "images" + File.separator + "home" + File.separator;
    // 心意卡商城内轮播图存放地址
    public static final String SAVE_HCARD_INSIDE_DIR = File.separator + "images" + File.separator + "inside" + File.separator;
    // 心意卡优惠卷图片存放地址
    public static final String SAVE_HCARD_COUPON_DIR = File.separator + "images" + File.separator + "coupon" + File.separator;
    // 小程序码图片存放地址
    public static final String SAVE_QR_CODE_DIR = File.separator + "images" + File.separator + "qrCode" + File.separator;
    // 海报图片存放地址
    public static final String SAVE_POSTER_DIR = File.separator + "images" + File.separator + "poster" + File.separator;

    public static final String STATIC = "/statics/";
}
