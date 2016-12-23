package org.guiceside.support.redis.session;

import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.redis.RedisStoreUtils;
import org.guiceside.support.redis.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenjiaWang on 15/8/8.
 */
public class RedisSessionUtils implements Serializable {
    public static final int DB_INDEX = 0;

    public static final String Suffix = "_redisSession";


    public static void refreshExpire(RedisSession redisSession) throws RedisSessionException {
        if (redisSession != null) {
            long startTime = redisSession.getCreationTime();
            long currentTime = System.currentTimeMillis();
            int maxInactiveInterval = redisSession.getMaxInactiveInterval();
            long diffTime = currentTime - startTime;
            diffTime = diffTime * 1000;
            long half = maxInactiveInterval / 2;
            if (diffTime <= half) {
                redisSession.setMaxInactiveInterval(maxInactiveInterval);
            }
            redisSession.setLastAccessedTime(currentTime);
        }
    }

    public static RedisSession getSession(JedisPool pool, String sessionID) throws RedisSessionException {
        RedisSession redisSession = null;
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                boolean flag = false;
                boolean exists = false;
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX);
                    try {
                        exists = RedisStoreUtils.exists(jedis, (sessionID + Suffix).getBytes());
                        if (exists) {
                            redisSession = getCurrentSession(jedis, sessionID);
                            if (redisSession != null) {
                                long currentTime = System.currentTimeMillis();
                                redisSession.setPool(pool);
                                redisSession.setLastAccessedTime(currentTime);
                            }
                        } else {
                            newSession(jedis, sessionID);
                        }
                    } catch (RedisSessionException e) {
                        flag = true;
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                        if (!flag && exists) {
                            refreshExpire(redisSession);
                        }
                    }
                }
            }
        }
        return redisSession;
    }

    private static RedisSession newSession(Jedis jedis, String sessionID) throws RedisSessionException {
        RedisSession redisSession = null;
        if (StringUtils.isNotBlank(sessionID)) {
            try {
                long currentTime = System.currentTimeMillis();
                redisSession = new RedisSession(sessionID);
                redisSession.setCreationTime(currentTime);
                redisSession.setLastAccessedTime(currentTime);
                redisSession.setNewSession(true);
                Map<byte[], byte[]> map = new HashMap<>();
                map.put(RedisSession.class.getName().getBytes(), SerializeUtil.serialize(redisSession));
                RedisStoreUtils.hmset(jedis, (sessionID + Suffix).getBytes(), map);
                RedisStoreUtils.expire(jedis, (sessionID + Suffix).getBytes(), redisSession.getMaxInactiveInterval());
            } catch (RedisSessionException e) {
            }
        }
        return redisSession;
    }


    private static RedisSession getCurrentSession(Jedis jedis, String sessionID) throws RedisSessionException {
        RedisSession redisSession = null;
        if (StringUtils.isNotBlank(sessionID)) {
            try {
                Object obj = RedisStoreUtils.hget(jedis, (sessionID + Suffix).getBytes(), RedisSession.class.getName().getBytes());
                if (obj != null) {
                    redisSession = (RedisSession) obj;
                }
            } catch (RedisSessionException e) {
            }

        }
        return redisSession;
    }


    public static void clearSession(JedisPool pool, String sessionID) throws RedisSessionException {
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX);
                    try {
                        RedisStoreUtils.del(jedis, (sessionID + Suffix).getBytes());
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
}
