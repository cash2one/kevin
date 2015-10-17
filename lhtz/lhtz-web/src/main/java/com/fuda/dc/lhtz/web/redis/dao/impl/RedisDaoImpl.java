package com.fuda.dc.lhtz.web.redis.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fuda.dc.lhtz.web.redis.dao.RedisDao;

@Repository
public class RedisDaoImpl implements RedisDao {

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    @Override
    public void set(final String key, final String value) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize(key), redisTemplate.getStringSerializer()
                        .serialize(value));
                return null;
            }
        });
    }

    @Override
    public void set(final byte[] key, final byte[] value) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                return null;
            }
        });
    }

    @Override
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] data = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(data)) {
                    byte[] data1 = connection.get(data);
                    String value = redisTemplate.getStringSerializer().deserialize(data1);
                    return value;
                }
                return null;
            }
        });
    }

    @Override
    public byte[] get(final byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key);
            }
        });
    }

    @Override
    public boolean exists(final byte[] key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key);
            }
        });
    }

    @Override
    public void delete(Serializable key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(Collection<Serializable> keys) {
        redisTemplate.delete(keys);
    }
    
    @Override
    public boolean expire(Serializable key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    @Override
    public void rename(Serializable oldKey, Serializable newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    @Override
    public long getExpire(Serializable key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public long getExpire(Serializable key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

}
