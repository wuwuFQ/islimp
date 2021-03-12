package com.javakc.islimp.commons.mybatis;

import com.javakc.islimp.commons.redis.RedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: islimp
 * @description: 建立Ioc与Mybatis依赖的中转
 * @author: zhou hong gang
 * @create: 2020-12-14 14:33
 */
@Component
public class MybatisRedisTransfer {

    @Autowired
    public MybatisRedisTransfer(RedisComponent redisComponent) {
        MybatisRedisCache.redisComponent = redisComponent;
    }

}
