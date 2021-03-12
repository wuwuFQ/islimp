package com.javakc.islimp.commons.security;

import com.javakc.islimp.commons.base.response.ResultCode;
import com.javakc.islimp.commons.base.response.ResultMessage;
import com.javakc.islimp.commons.redis.RedisComponent;
import com.javakc.islimp.commons.security.filter.LoginFilter;
import com.javakc.islimp.commons.security.filter.PermissionFilter;
import com.javakc.islimp.commons.security.util.TokenUtils;
import com.javakc.islimp.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: islimp
 * @description: SpringSecurity安全配置
 * @author: zhou hong gang
 * @create: 2020-12-15 09:48
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密管理
     * @return BCryptPasswordEncoder
     */
    @Bean("passwordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入UserDetailsService
     */
    @Autowired
    @Qualifier("userDetailsService")
    public UserDetailsService userDetailsService;

    /**
     * 注入token工具类
     */
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 注入redis工具类
     */
    @Autowired
    private RedisComponent redisComponent;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * 跨域(application)
         */
        http.cors();
        /*
         * 当前Spring Security使用JWT(JSON WEB TOKEN)实现
         * 关闭Spring Security csrf(跨站点请求伪造)
         */
        http.csrf().disable();
        /*
         * spring security会话管理
         * SessionCreationPolicy.ALWAYS         Spring Security一直创建 HttpSession
         * SessionCreationPolicy.NEVER          Spring Security不会创建HttpSession，但如果它已经存在则使用HttpSession
         * SessionCreationPolicy.IF_REQUIRED    Spring Security只会在需要时创建一个HttpSession
         * SessionCreationPolicy.STATELESS      Spring Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
            //swagger放行
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/v3/api-docs").permitAll()
            //druid放行
            .antMatchers("/druid/**").permitAll()
            //其他一律需要认证
            .anyRequest().authenticated();

        http.exceptionHandling()
            //用户未登录异常(没有经过认证)
            .authenticationEntryPoint((request, response, exception) -> {
                ResultMessage.response(response, ResultMessage.failure(ResultCode.USER_NOT_LOGIN));
            });
            //已经经过认证但权限不够
//            .accessDeniedHandler((request, response, exception) ->{
//                System.out.println("权限不足...");
////                ResultMessage.response(response, ResultMessage.failure(ResultCode.USER_NOT_LOGIN));
//            });
        http
            //添加登陆过滤器 拦截用户对于 /login 的请求
            .addFilter(new LoginFilter(tokenUtils, redisComponent, authenticationManager()))
            .addFilter(new PermissionFilter(tokenUtils, redisComponent, userDetailsService, authenticationManager()));
    }



    /**
     * 构建认证管理器
     * @param auth 认证管理构建对象
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        在内存中 模拟数据库的账号 角色等信息
//        auth.inMemoryAuthentication()
//            .passwordEncoder(bCryptPasswordEncoder())
//            .withUser("admin").password("$2a$10$oMINcMW8Tky/uQsGRukDQ.N/sQzi5ym6mL/vm/nVVvs9Ej5HkBMRa").roles("ADMINISTRATOR")
//            .and()
//            .withUser("user").password("123456").roles("NORMAL");

//        直接连接数据库查询账号密码
//        auth
//            .userDetailsService(userDetailsService)
//            .passwordEncoder(bCryptPasswordEncoder());

        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //不要隐藏用户找不到异常
        provider.setHideUserNotFoundExceptions(false);
        //根据登陆账号查询用户信息
        provider.setUserDetailsService(userDetailsService);
        //如果用户存在则匹配密码
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

}
