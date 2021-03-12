package com.javakc.islimp.commons.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: islimp
 * @description: token管理工具类
 * @author: zhou hong gang
 * @create: 2020-12-16 09:55
 */
@Component
public class TokenUtils {

    private TokenProperties tokenProperties;

    @Autowired
    public TokenUtils(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    /**
     * 生成认证token
     * @param username  账号
     * @return 认证token
     */
    public String createToken(String username, long time)
    {
        return Jwts.builder()
                //签发标识
                .setIssuer(tokenProperties.getIssuer())
                //权限集合
//                .setClaims(claims)
                //签发账号
                .setSubject(username)
                //设置签发时间
                .setIssuedAt(new Date())
                //设置签名秘钥 加盐
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret())
                //设置签发过期时间(毫秒)
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
                .compact();
    }

    /**
     * 从请求中获取token
     * @param request 请求
     * @return token
     */
    public String getToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader(tokenProperties.getHeader());
        if(StringUtils.hasLength(bearerToken))
        {
            if(bearerToken.startsWith(tokenProperties.getPrefix()))
            {
                return bearerToken.substring(tokenProperties.getPrefix().length());
            }
        }
        return null;
    }

    /**
     * 根据token获取登陆名称
     * @param token token
     * @return username
     */
    public String getUsername(String token)
    {
        return getTokenClaims(token).getSubject();
    }

    /**
     * 根据token验证是否过期
     * @param token 请求token
     * @return 是否过期
     */
    public boolean isExpiration(String token){
        return getTokenClaims(token).getExpiration().before(new Date());
    }

    /**
     * 根据token获取签署认证信息
     * @param token 请求token
     * @return 签署认证信息
     */
    public Claims getTokenClaims(String token){
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

}
