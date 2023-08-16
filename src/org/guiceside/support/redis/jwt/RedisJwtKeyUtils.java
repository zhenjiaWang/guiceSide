package org.guiceside.support.redis.jwt;

import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.redis.RedisStoreUtils;
import org.guiceside.support.redis.session.RedisSessionException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

/**
 * Created by zhenjiaWang on 15/8/8.
 */
public class RedisJwtKeyUtils implements Serializable {
    public static final int DB_INDEX_USERS_SESSIONID_KEY = 1;

    public static boolean createKey(JedisPool pool, String jwtKEY, Serializable value, boolean override) throws RedisSessionException {
        boolean exists = false;
        if (pool != null) {

            Jedis jedis = null;
            if (StringUtils.isNotBlank(jwtKEY)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_USERS_SESSIONID_KEY);
                    exists = RedisStoreUtils.exists(jedis, jwtKEY.getBytes());
                    if (!exists) {
                        RedisStoreUtils.set(jedis, jwtKEY.getBytes(), value);
                    } else {
                        if (override) {
                            RedisStoreUtils.set(jedis, jwtKEY.getBytes(), value);
                        }
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
        return exists;
    }

    public static Serializable getKey(JedisPool pool, String jwtKEY) throws RedisSessionException {
        Serializable serializable = null;
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(jwtKEY)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_USERS_SESSIONID_KEY);
                    boolean exists = RedisStoreUtils.exists(jedis, jwtKEY.getBytes());
                    if (exists) {
                        serializable = RedisStoreUtils.get(jedis, jwtKEY.getBytes());
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
        return serializable;
    }


}
