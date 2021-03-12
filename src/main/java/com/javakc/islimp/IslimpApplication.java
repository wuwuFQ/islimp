package com.javakc.islimp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class IslimpApplication {

    public static void main(String[] args) {
        SpringApplication.run(IslimpApplication.class, args);
    }

}
