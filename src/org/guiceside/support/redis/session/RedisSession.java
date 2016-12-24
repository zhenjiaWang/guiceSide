package org.guiceside.support.redis.session;

import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.redis.RedisStoreUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * Created by zhenjiawang on 2016/12/23.
 */
public class RedisSession implements HttpSession {

    private String id;

    private String key;

    private long creationTime;

    private long lastAccessedTime;

    private int maxInactiveInterval;

    private boolean newSession;

    private boolean available;

    private JedisPool pool;

    private RedisSession() {

    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public RedisSession(String id) {
        this.id = id;
        maxInactiveInterval = 60 * 120;
        this.key = this.id + RedisSessionUtils.Suffix;
        this.available = false;
    }

    public RedisSession(String id, int maxInactiveInterval) {
        this.id = id;
        this.maxInactiveInterval = maxInactiveInterval;
        this.key = this.id + RedisSessionUtils.Suffix;
        this.available = false;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void invalidate() {
        if (pool != null && maxInactiveInterval > 0) {
            try {
                RedisSessionUtils.clearSession(pool, id);
                available = true;
            } catch (RedisSessionException e) {

            }
        }
    }

    @Override
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        if (pool != null && maxInactiveInterval > 0 && !available) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(id)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(RedisSessionUtils.DB_INDEX);
                    try {
                        RedisStoreUtils.expire(jedis, key.getBytes(), maxInactiveInterval);
                        this.maxInactiveInterval = maxInactiveInterval;
                    } catch (RedisSessionException e) {
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
    }

    @Override
    public void setAttribute(String attr, Object attrValue) {
        if (pool != null && !available) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(attr) && attrValue != null) {
                boolean flag = false;
                try {
                    jedis = pool.getResource();
                    jedis.select(RedisSessionUtils.DB_INDEX);
                    try {
                        RedisStoreUtils.hset(jedis, key.getBytes(), attr.getBytes(), attrValue);
                    } catch (RedisSessionException e) {
                        flag = true;
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                        if (!flag) {
                            RedisSessionUtils.refreshExpire(this);
                        }
                    }
                }
            }
        }
    }



    @Override
    public String getAttribute(String attr) {
        String attrValue = null;
        if (pool != null && !available) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(attr)) {
                boolean flag = false;
                try {
                    jedis = pool.getResource();
                    jedis.select(RedisSessionUtils.DB_INDEX);
                    try {
                        Object obj = RedisStoreUtils.hget(jedis, key.getBytes(), attr.getBytes());
                        if (obj != null) {
                            attrValue = obj.toString();
                        }
                    } catch (RedisSessionException e) {
                        flag = true;
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                        if (!flag) {
                            RedisSessionUtils.refreshExpire(this);
                        }
                    }
                }
            }
        }
        return attrValue;
    }

    public Object getAttributeForObj(String attr) {
        Object obj = null;
        if (pool != null && !available) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(attr)) {
                boolean flag = false;
                try {
                    jedis = pool.getResource();
                    jedis.select(RedisSessionUtils.DB_INDEX);
                    try {
                        obj = RedisStoreUtils.hget(jedis, key.getBytes(), attr.getBytes());
                    } catch (RedisSessionException e) {
                        flag = true;
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                        if (!flag) {
                            RedisSessionUtils.refreshExpire(this);
                        }
                    }
                }
            }
        }
        return obj;
    }

    public <T> T  getAttributeForType(String attr,Class<T> classType) {
        Object value = null;
        if (pool != null && !available) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(attr)&&classType!=null) {
                boolean flag = false;
                try {
                    jedis = pool.getResource();
                    jedis.select(RedisSessionUtils.DB_INDEX);
                    try {
                        Object obj = RedisStoreUtils.hget(jedis, key.getBytes(), attr.getBytes());
                        if (obj != null) {
                            value = BeanUtils.convertValue(obj, classType);
                        }
                    } catch (RedisSessionException e) {
                        flag = true;
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                        if (!flag) {
                            RedisSessionUtils.refreshExpire(this);
                        }
                    }
                }
            }
        }
        return value == null ? null : (T) value;
    }

    @Override
    public void removeAttribute(String attr) {
        if (pool != null && !available) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(id)) {
                boolean flag = false;
                try {
                    jedis = pool.getResource();
                    jedis.select(RedisSessionUtils.DB_INDEX);
                    try {
                        RedisStoreUtils.hdel(jedis,key.getBytes(),attr.getBytes());
                    } catch (RedisSessionException e) {
                        flag = true;
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                        if (!flag) {
                            RedisSessionUtils.refreshExpire(this);
                        }
                    }
                }
            }
        }
    }


    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }


    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }


    @Override
    public boolean isNew() {
        return newSession;
    }

    public void setNewSession(boolean newSession) {
        this.newSession = newSession;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void putValue(String s, Object o) {

    }

    @Override
    public void removeValue(String s) {

    }
}
