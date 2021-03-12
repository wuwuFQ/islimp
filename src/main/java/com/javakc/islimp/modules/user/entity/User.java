package com.javakc.islimp.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javakc.islimp.commons.base.entity.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: islimp
 * @description: RBAC用户
 * @author: zhou hong gang
 * @create: 2020-12-15 13:43
 */
@Getter
@Setter
@TableName(value = "user_info")
@ApiModel(value = "用户模块")
public class User extends Base {
    @ApiModelProperty(name = "userName", value = "姓名")
    private String userName;
    @ApiModelProperty(name = "userCard", value = "身份证")
    private String userCard;
    @ApiModelProperty(name = "userAccount", value = "账号")
    private String userAccount;
    @ApiModelProperty(name = "userPassword", value = "密码")
    private String userPassword;
    @ApiModelProperty(name = "userLock", value = "是否锁定")
    private Integer userLock;
}
