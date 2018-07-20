package org.guiceside.support.redis.session;

import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.redis.RedisStoreUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhenjiaWang on 15/8/8.
 */
public class RedisSessionUtils implements Serializable {
    public static final int DB_INDEX_SESSION = 0;

    public static final String Suffix = "_redisSession";

    public static void refreshExpire(Jedis jedis, RedisUserInfo redisUserInfo) throws RedisSessionException {
        if (redisUserInfo != null) {
            long startTime = redisUserInfo.getCreateTimes();
            long currentTime = System.currentTimeMillis();
            int maxInactiveInterval = redisUserInfo.getMaxInactiveInterval();
            long diffTime = currentTime - startTime;
            diffTime = diffTime * 1000;
            long half = maxInactiveInterval / 2;
            if (diffTime <= half) {
                RedisStoreUtils.expire(jedis, (redisUserInfo.getSessionId() + Suffix).getBytes(), redisUserInfo.getMaxInactiveInterval());
            }
            RedisStoreUtils.hset(jedis, (redisUserInfo.getSessionId() + Suffix), "lastAccessedTime", currentTime + "");
        }
    }


    public static RedisUserInfo getUserInfo(JedisPool pool, String sessionID) throws RedisSessionException {
        RedisUserInfo redisUserInfo = null;
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                boolean flag = false;
                boolean exists = false;
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_SESSION);
                    try {
                        exists = RedisStoreUtils.exists(jedis, (sessionID + Suffix));
                        if (exists) {
                            redisUserInfo = getCurrentUserInfo(jedis, sessionID);
                        }
                    } catch (RedisSessionException e) {
                        flag = true;
                    }
                } finally {
                    if (jedis != null) {
                        if (!flag && exists) {
                            refreshExpire(jedis, redisUserInfo);
                        }
                        jedis.close();
                        if (!exists) {
                            throw new RedisSessionException();
                        }
                    }
                }
            }
        }
        return redisUserInfo;
    }


    public static RedisSession newSession(JedisPool pool, int maxInactiveInterval) throws RedisSessionException {
        RedisSession redisSession = null;
        if (pool != null) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                jedis.select(DB_INDEX_SESSION);
                long currentTime = System.currentTimeMillis();
                String sessionID = SessionUtil.generateSessionId();
                redisSession = new RedisSession(sessionID, maxInactiveInterval);
                redisSession.setCreationTime(currentTime);
                redisSession.setLastAccessedTime(currentTime);
                redisSession.setNewSession(true);
                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                map.put("creationTime", currentTime + "");
                map.put("lastAccessedTime", currentTime + "");
                map.put("newSession", true + "");
                map.put("sessionID", sessionID + "");
                map.put("maxInactiveInterval", maxInactiveInterval + "");
                RedisStoreUtils.hmset(jedis, (sessionID + Suffix), map);
                RedisStoreUtils.expire(jedis, (sessionID + Suffix), redisSession.getMaxInactiveInterval());
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return redisSession;
    }


    private static RedisUserInfo getCurrentUserInfo(Jedis jedis, String sessionID) throws RedisSessionException {
        RedisUserInfo redisUserInfo = null;
        if (StringUtils.isNotBlank(sessionID)) {
            try {
                Long userId = RedisStoreUtils.hgetLong(jedis, (sessionID + Suffix), "userId");
                if (userId != null) {
                    redisUserInfo = new RedisUserInfo();
                    redisUserInfo.setUserId(userId);
                    redisUserInfo.setSessionId(sessionID);
                    String ip = RedisStoreUtils.hgetString(jedis, (sessionID + Suffix), "createIp");
                    redisUserInfo.setCreateIp(ip);
                    String browser = RedisStoreUtils.hgetString(jedis, (sessionID + Suffix), "browser");
                    redisUserInfo.setBrowser(browser);
                    String os = RedisStoreUtils.hgetString(jedis, (sessionID + Suffix), "os");
                    redisUserInfo.setOs(os);
                    Long lastAccessedTime = RedisStoreUtils.hgetLong(jedis, (sessionID + Suffix), "lastAccessedTime");
                    redisUserInfo.setLastAccessedTime(lastAccessedTime);
                    Integer maxInactiveInterval = RedisStoreUtils.hgetInteger(jedis, (sessionID + Suffix), "maxInactiveInterval");
                    redisUserInfo.setMaxInactiveInterval(maxInactiveInterval);
                }
            } catch (RedisSessionException e) {
            }

        }
        return redisUserInfo;
    }

    public static void removeAttr(JedisPool pool, String sessionID, String key) throws RedisSessionException {
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_SESSION);
                    RedisStoreUtils.hdel(jedis, (sessionID + Suffix), key);
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
    }

    public static String getAttr(JedisPool pool, String sessionID, String key) throws RedisSessionException {
        String value = null;
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_SESSION);
                    value = RedisStoreUtils.hgetString(jedis, (sessionID + Suffix), key);
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
        return value;
    }

    public static <T> T getAttr(JedisPool pool, String sessionID, String key, Class<T> type) throws RedisSessionException {
        Object result = null;
        String value = null;
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_SESSION);
                    value = RedisStoreUtils.hgetString(jedis, (sessionID + Suffix), key);
                    if (StringUtils.isNotBlank(value)) {
                        result = BeanUtils.convertValue(value, type);
                    }

                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
        return (T) result;
    }

    public static void setAttr(JedisPool pool, String sessionID, String key, String value) throws RedisSessionException {
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_SESSION);
                    RedisStoreUtils.hset(jedis, (sessionID + Suffix), key, value);
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
    }


    public static void clearSession(JedisPool pool, String sessionID) throws RedisSessionException {
        if (pool != null) {
            Jedis jedis = null;
            if (StringUtils.isNotBlank(sessionID)) {
                try {
                    jedis = pool.getResource();
                    jedis.select(DB_INDEX_SESSION);
                    try {
                        RedisStoreUtils.del(jedis, (sessionID + Suffix));
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
