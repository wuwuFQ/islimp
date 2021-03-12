package com.javakc.islimp.commons.security.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: islimp
 * @description: token属性管理
 * @author: zhou hong gang
 * @create: 2020-12-16 10:03
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.security.jwt")
public class TokenProperties {
    private String header;
    private String prefix;
    private String secret;
    private String issuer;
    private long expiration;
    private long remember;
    private String claimsAuthorities;
}
