package com.javakc.islimp.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javakc.islimp.modules.user.entity.User;

import java.util.List;

/**
 * @program: islimp
 * @description: RBAC用户数据层
 * @author: zhou hong gang
 * @create: 2020-12-15 13:43
 */
public interface UserDao extends BaseMapper<User> {
    List<String> queryPermissionByName(String name);
}
