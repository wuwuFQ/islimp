package com.javakc.islimp.commons.security.filter;

import com.javakc.islimp.commons.redis.RedisComponent;
import com.javakc.islimp.commons.security.util.TokenUtils;
import com.javakc.islimp.modules.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @program: islimp
 * @description: 权限认证过滤器
 * @author: zhou hong gang
 * @create: 2020-12-16 13:43
 */
public class PermissionFilter extends BasicAuthenticationFilter {

    private TokenUtils tokenUtils;
    private RedisComponent redisComponent;
    private UserService userDetailsService;

    public PermissionFilter(TokenUtils tokenUtils, RedisComponent redisComponent, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.tokenUtils = tokenUtils;
        this.redisComponent = redisComponent;
        this.userDetailsService = (UserService) userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //从请求头中获取token
        String token = tokenUtils.getToken(request);
        if(!StringUtils.hasLength(token))
        {
            chain.doFilter(request, response);
            return;
        }
        //获取认证对象
        Authentication authentication = getAuthentication(token, request, response);
        //告诉spring security认证成功
        SecurityContextHolder.getContext().setAuthentication( authentication );
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 认证环节
     * @param token token
     * @param request 请求
     * @param response 响应
     * @return 认证
     */
    public Authentication getAuthentication(String token, HttpServletRequest request, HttpServletResponse response)
    {
        //1.从token中获取登陆的用户
        String username = tokenUtils.getUsername(token);
        if(StringUtils.hasLength(username))
        {
            List<String> permissionList = userDetailsService.queryPermissionByName(username);
            Collection<SimpleGrantedAuthority> authorityCollection = new ArrayList<>();
            permissionList.stream().forEach(permission -> {
                authorityCollection.add(new SimpleGrantedAuthority(permission));
            });

//            根据token有效期判断
//            if(!tokenUtils.isExpiration(token))
//            验证redis中是否存在 即是否在有效期内
            if(redisComponent.exists(username+":access_token")) {
                //2.创建token认证对象
                redisComponent.set(username + ":access_token", token,3600);

//                Date date = new Date();
//                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//                String dateStr = format.format(date);
//
//                redisComponent.hset(username+":access_frequencies", format.format(date), 1);
//                redisComponent.hincr(username+":access_frequencies", format.format(date), 1);

//                1: 普通的用户(登陆频率) a 1 r 24
//                1.8 a 1*1.8 r 24*1.8

//                redisComponent.incr(username+":access_frequencies", 1);
                return new UsernamePasswordAuthenticationToken(username, null, authorityCollection);
            }
            else if(redisComponent.exists(username+":refresh_token")) {
                redisComponent.set(username + ":access_token", token,3600);
                redisComponent.set(username+":refresh_token", redisComponent.get(username+":refresh_token"), 3600*24);
//                redisComponent.incr(username+":access_frequencies", 1);
                return new UsernamePasswordAuthenticationToken(username, null, authorityCollection);
            }
        }
        return null;
    }

}
