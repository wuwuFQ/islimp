package com.javakc.islimp.commons.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javakc.islimp.commons.base.response.ResultCode;
import com.javakc.islimp.commons.base.response.ResultMessage;
import com.javakc.islimp.commons.redis.RedisComponent;
import com.javakc.islimp.commons.security.util.TokenUtils;
import com.javakc.islimp.modules.user.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @program: islimp
 * @description: 登陆过滤器
 * @author: zhou hong gang
 * @create: 2020-12-15 14:27
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private TokenUtils tokenUtils;
    private RedisComponent redisComponent;
    private AuthenticationManager authenticationManager;

    public LoginFilter(TokenUtils tokenUtils, RedisComponent redisComponent, AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.redisComponent = redisComponent;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 拦截客户端登陆请求
     * @param request 请求
     * @param response 响应
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserAccount(), user.getUserPassword(), new ArrayList<>());
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 登陆成功后的回调函数
     * @param request   请求
     * @param response  响应
     * @param chain     过滤器链
     * @param authentication    认证结果
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //获取登陆名称(唯一)
        String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();

        /**
         * 1.access_token   1小时     访问token
         * 2.refresh_token  24小时    刷新token
         */
        String access_token = tokenUtils.createToken(username, 3600);
        String refresh_token = tokenUtils.createToken(username, 3600*24);

        redisComponent.set(username+":access_token", access_token, 3600);
        redisComponent.set(username+":refresh_token", refresh_token, 3600*24);
//        redisComponent.set(username+":access_frequencies", 0);

        Map<String, String> tokenMap = Map.of("access_token", access_token, "refresh_token", refresh_token);
        ResultMessage.response(response, ResultMessage.success(ResultCode.USER_LOGIN_SUCCESS, tokenMap));
    }

    /**
     * 登陆失败后的回调函数
     * @param request   请求
     * @param response  响应
     * @param failed    认证异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        ResultMessage message = null;
        if(failed instanceof UsernameNotFoundException)
            message = ResultMessage.failure(ResultCode.USER_NOT_EXIST);
        else if(failed instanceof BadCredentialsException)
            message = ResultMessage.failure(ResultCode.USER_PASS_ERROR);
        else if(failed instanceof InternalAuthenticationServiceException)
            message = ResultMessage.failure(ResultCode.USER_ACCOUNT_LOCKED);
        else
            message = ResultMessage.failure(ResultCode.USER_LOGIN_OTHER_ERROR);
        ResultMessage.response(response, message);
    }

}
