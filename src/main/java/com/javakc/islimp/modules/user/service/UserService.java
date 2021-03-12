package com.javakc.islimp.modules.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javakc.islimp.commons.base.service.BaseService;
import com.javakc.islimp.modules.user.dao.UserDao;
import com.javakc.islimp.modules.user.entity.User;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: islimp
 * @description: RBAC用户逻辑层
 * @author: zhou hong gang
 * @create: 2020-12-15 13:48
 */
@Service(value = "userDetailsService")
@Transactional(readOnly = true)
public class UserService extends BaseService<UserDao, User> implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        //封装登陆账号查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_account", userAccount);
        //根据登陆账号查询对象
        User user = baseMapper.selectOne(wrapper);
        if(ObjectUtils.isEmpty(user))
        {
            throw new UsernameNotFoundException("很遗憾, 账号不存在!");
        }
        else
        {
            if(user.getUserLock() == 1)
            {
                throw new LockedException("很遗憾, 账号已被锁定!");
            }
            /**
             * 1.登陆账号
             * 2.登陆密码
             * 3.角色权限 ================== 认证成功后获取用户授权列表
             */
            return new org.springframework.security.core.userdetails.User(userAccount, user.getUserPassword(), new ArrayList<>());
        }
    }

    public List<String> queryPermissionByName(String name)
    {
        return baseMapper.queryPermissionByName(name);
    }
}
