package io.renren.modules.app.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jgl
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="UserPO对象", description="用户表")
public class UserPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("nick_name")
    private String nickName;

    @TableField("face_icon")
    private String faceIcon;

    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
    @TableField("gender")
    private Integer gender;

    @TableField("city")
    private String city;

    @TableField("province")
    private String province;

    @TableField("country")
    private String country;

    @ApiModelProperty(value = "SessionID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "手机号")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private String birthday;

    @ApiModelProperty(value = "身份证号")
    @TableField("idNumber")
    private String idNumber;

    @ApiModelProperty(value = "姓名")
    @TableField("realName")
    private String realName;

    @ApiModelProperty(value = "是否是会员 0有课 1会员")
    @TableField("isMember")
    private String isMember;

    @ApiModelProperty(value = "是否删除 0否 1是")
    @TableField("isDel")
    private Integer isDel;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty(value = "最近登录时间")
    @TableField("endLoginTime")
    private Date endLoginTime;


}
