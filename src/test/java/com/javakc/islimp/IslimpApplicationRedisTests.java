package com.javakc.islimp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;

@SpringBootTest
class IslimpApplicationRedisTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        //1.从redisTemplate中获取数据类型

        //1.1获取string存储类型
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("name", "丁浩");
//        valueOperations.set("name", Map.of("key1", "value1", "key2", "value2"));

        //1.2获取操作hash的接口
//        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
//        hashOperations.putAll("car:1", Map.of("color", "白色", "price", 900000, "type", 1));

        //1.3获取List
//        ListOperations<String, Object> listOperations = redisTemplate.opsForList();

        //1.4获取Set
//        redisTemplate.opsForSet();

        //1.5获取Zset
//        redisTemplate.opsForZSet();

        //1.6获取HyperLogLog
//        redisTemplate.opsForHyperLogLog();

//        redisTemplate.delete("name");
    }

}
