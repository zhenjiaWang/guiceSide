package org.guiceside.support.redis.session;

import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.redis.RedisStoreUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

/**
 * Created by zhenjiaWang on 15/8/8.
 */
public class RedisUserSessionKeyUtils implements Serializable {
    public static final int DB_INDEX_USERS_SESSIONID_KEY = 1;

    public static String createUserKey(JedisPool pool, String userKey, String value, boolean override) throws RedisSessionException {
        String oldSessionID = null;
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(userKey)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_USERS_SESSIONID_KEY);
                    boolean exists = RedisStoreUtils.exists(jedis, userKey);
                    if (!exists) {
                        RedisStoreUtils.set(jedis, userKey, value);
                    } else {
                        oldSessionID = RedisStoreUtils.getString(jedis, userKey);
                        if (override) {
                            RedisStoreUtils.set(jedis, userKey, value);
                        }
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
        return oldSessionID;
    }

    public static String deleteUserKey(JedisPool pool, String userKey) throws RedisSessionException {
        String oldSessionID = null;
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(userKey)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_USERS_SESSIONID_KEY);
                    boolean exists = RedisStoreUtils.exists(jedis, userKey);
                    if (exists) {
                        oldSessionID = RedisStoreUtils.getString(jedis, userKey);
                        RedisStoreUtils.del(jedis, userKey);
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
        return oldSessionID;
    }
}
