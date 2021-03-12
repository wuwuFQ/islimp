package com.javakc.islimp.commons.mybatis;

import com.javakc.islimp.commons.redis.RedisComponent;
import org.apache.ibatis.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: islimp
 * @description: mybatis使用redis作为二级缓存目标
 * @author: zhou hong gang
 * @create: 2020-12-14 14:21
 */
public class MybatisRedisCache implements Cache {

    private String id;
    public static RedisComponent redisComponent;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public MybatisRedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * 查询数据库的记录后, 调用put写入数据到二级缓存中
     * @param key   sql
     * @param value result
     */
    @Override
    public void putObject(Object key, Object value) {
        redisComponent.hset(id, key.toString(), value);
    }

    /**
     * 每次执行二级缓存查询的方法
     * @param key sql
     * @return result/null
     */
    @Override
    public Object getObject(Object key) {
        return redisComponent.hget(id, key.toString());
    }

    /**
     * 执行数据删除
     * @param key sql
     * @return
     */
    @Override
    public Object removeObject(Object key) {
        redisComponent.hdel(id, key.toString());
        return null;
    }

    @Override
    public void clear() {
        redisComponent.del(id);
    }

    @Override
    public int getSize() {
        return redisComponent.hkey(id).size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

}
