package com.javakc.islimp.commons.base.cors;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: islimp
 * @description: 跨域配置
 * @author: zhou hong gang
 * @create: 2020-12-09 11:54
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "authFilter")
public class CorsConfig implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
        httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        filterChain.doFilter(servletRequest, httpResponse);
    }

}
