package org.guiceside.support.redis.session;

import org.guiceside.support.redis.RedisPoolProvider;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

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

    public final static String SESSION_NAME = "userSession";

    public final static JedisPool jedisPool=RedisPoolProvider.getPool("REDIS_SESSION");
    public static RedisUserInfo create(String sessionID) throws RedisSessionException {
        HttpSession session = RedisSessionUtils.getSession(jedisPool,sessionID);
        if(session!=null){
            RedisUserInfo redisUserInfo = new RedisUserInfo();
            redisUserInfo.setLanguagePreference(RedisUserInfo.DEFAULT_LANGUAGE_PREFERENCE);
            redisUserInfo.setCountryPreference(RedisUserInfo.DEFAULT_COUNTRY_PREFERENCE);
            redisUserInfo.setSessionId(session.getId());
            session.setAttribute(RedisUserSession.SESSION_NAME, redisUserInfo);
            session.setMaxInactiveInterval(60 * 120);
            return redisUserInfo;
        }
        return null;
    }

    public static RedisUserInfo getUserInfo(String sessionID) throws RedisSessionException {
        HttpSession session = RedisSessionUtils.getSession(jedisPool,sessionID);
        RedisUserInfo redisUserInfo = (RedisUserInfo) session.getAttribute(SESSION_NAME);
        if (redisUserInfo == null) {
            throw new RedisSessionException();
        }
        return redisUserInfo;
    }

    public static void save(String sessionID,RedisUserInfo redisUserInfo) {
        HttpSession session = RedisSessionUtils.getSession(jedisPool,sessionID);
        if (session != null){
            session.setAttribute(RedisUserSession.SESSION_NAME, redisUserInfo);
        }
    }

    public static void removeAttribute( String sessionID, String key) {
        HttpSession session = RedisSessionUtils.getSession(jedisPool,sessionID);
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    public static Object getAttribute(String sessionID, String key) {
        HttpSession session = RedisSessionUtils.getSession(jedisPool,sessionID);
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    public static void setAttribute(String sessionID, String key, Object obj) {
        HttpSession session = RedisSessionUtils.getSession(jedisPool,sessionID);
        if (session != null) {
            session.setAttribute(key, obj);
        }
    }


    public static void invalidate(String sessionID) {
        HttpSession session = RedisSessionUtils.getSession(jedisPool,sessionID);
        if (session != null) {
            session.invalidate();
            session = null;
        }
    }
}