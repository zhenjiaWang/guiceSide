package org.guiceside.support.redis.session;

import org.guiceside.commons.HttpAccessUtils;
import org.guiceside.support.redis.RedisPoolProvider;
import org.guiceside.support.redis.RedisStoreUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import static org.guiceside.support.redis.session.RedisSessionUtils.DB_INDEX_SESSION;
import static org.guiceside.support.redis.session.RedisSessionUtils.Suffix;

/**
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 2008-10-30
 * @since JDK1.5
 */
public class RedisUserSession implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final static JedisPool jedisPool = RedisPoolProvider.getPool("REDIS_SESSION");


    public static RedisUserInfo create(HttpServletRequest httpServletRequest) throws RedisSessionException {
        return create(httpServletRequest, 60 * 120);
    }

    public static RedisUserInfo create(HttpServletRequest httpServletRequest, int maxInactiveInterval) throws RedisSessionException {
        RedisSession redisSession = RedisSessionUtils.newSession(jedisPool, maxInactiveInterval);
        if (redisSession != null) {
            RedisUserInfo redisUserInfo = new RedisUserInfo();
            redisUserInfo.setLanguagePreference(RedisUserInfo.DEFAULT_LANGUAGE_PREFERENCE);
            redisUserInfo.setCountryPreference(RedisUserInfo.DEFAULT_COUNTRY_PREFERENCE);
            redisUserInfo.setSessionId(redisSession.getId());
            String ip = HttpAccessUtils.getIp(httpServletRequest);
            redisUserInfo.setCreateIp(ip);
            String browser = HttpAccessUtils.getBrowser(httpServletRequest);
            redisUserInfo.setBrowser(browser);
            String os = HttpAccessUtils.getOS(httpServletRequest);
            redisUserInfo.setOs(os);
            redisUserInfo.setCreateTimes(System.currentTimeMillis());
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                jedis.select(DB_INDEX_SESSION);
                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                map.put("languagePreference", RedisUserInfo.DEFAULT_LANGUAGE_PREFERENCE);
                map.put("countryPreference", RedisUserInfo.DEFAULT_COUNTRY_PREFERENCE);
                map.put("createIp", ip);
                map.put("browser", browser);
                map.put("os", os);
                map.put("createTimes", redisUserInfo.getCreateTimes() + "");
                RedisStoreUtils.hmset(jedis, (redisSession.getId() + Suffix), map);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return redisUserInfo;
        }
        return null;
    }

    public static RedisUserInfo getUserInfo(String sessionID) throws RedisSessionException {
        RedisUserInfo redisUserInfo = RedisSessionUtils.getUserInfo(jedisPool, sessionID);
        if (redisUserInfo != null) {
            return redisUserInfo;
        }
        throw new RedisSessionException();
    }


    public static void removeAttribute(String sessionID, String key) {
        RedisSessionUtils.removeAttr(jedisPool, sessionID, key);
    }

    public static Serializable getAttribute(String sessionID, String key) {
        return RedisSessionUtils.getAttr(jedisPool, sessionID, key);
    }

    public static <T> T getAttribute(String sessionID, String key, Class<T> type) {
        return RedisSessionUtils.getAttr(jedisPool, sessionID, key, type);
    }

    public static void setAttribute(String sessionID, String key, String obj) {
        RedisSessionUtils.setAttr(jedisPool, sessionID, key, obj);
    }


    public static void invalidate(String sessionID) {
        RedisUserInfo redisUserInfo = RedisSessionUtils.getUserInfo(jedisPool, sessionID);
        if (redisUserInfo != null) {
            RedisSessionUtils.clearSession(jedisPool, sessionID);
        }
    }
}