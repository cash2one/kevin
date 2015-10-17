package com.fuda.dc.lhtz.web.redis.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface RedisDao {

    public void set(final String key, final String value);

    public void set(final byte[] key, final byte[] value);

    public String get(final String key);

    public byte[] get(final byte[] key);

    public boolean exists(final byte[] key);

    public void delete(Serializable key);

    public void delete(Collection<Serializable> keys);

    public boolean expire(Serializable key, long timeout, TimeUnit unit);

    public void rename(Serializable oldKey, Serializable newKey);

    public long getExpire(Serializable key);

    public long getExpire(Serializable key, TimeUnit unit);

}
