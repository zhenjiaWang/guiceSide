package org.guiceside.support.hsf;

import com.google.inject.Inject;
import net.sf.json.JSONObject;
import ognl.OgnlException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.IdEntity;
import org.guiceside.persistence.entity.Tracker;
import org.guiceside.support.converter.DateConverter;
import org.guiceside.support.redis.RedisPoolProvider;
import org.guiceside.support.redis.session.*;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.util.Date;

public class BaseBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    public <T> T getService(Class<T> serviceClass) throws Exception {
        return hsfServiceFactory.consumer(serviceClass);
    }

    public HttpSession getSession(String sessionID) throws RedisSessionException {
        RedisSession redisSession = null;
        if (StringUtils.isNotBlank(sessionID)) {
            JedisPool jedisPool = RedisPoolProvider.getPool("REDIS_SESSION");
            redisSession = RedisSessionUtils.getSession(jedisPool, sessionID);
            if (redisSession != null) {
                return redisSession;
            }
        }
        throw new RedisSessionException();
    }


    protected String get(Object entity, String property) {
        Object result = null;
        if (entity != null) {
            try {
                result = BeanUtils.getValue(entity, property);
            } catch (OgnlException e) {
                result = null;
            }
        }
        return StringUtils.defaultIfEmpty(result);
    }

    protected String getDate(Object entity, String property, String f) {
        Object result = null;
        if (entity != null) {
            try {
                result = BeanUtils.getValue(entity, property);
            } catch (OgnlException e) {
                result = null;
            }
        }
        return StringUtils.defaultIfEmptyByDate((Date) result, f);
    }

    protected <T> T get(Object entity, String property, Class<T> type) {
        Object result = null;
        if (entity != null) {
            try {
                result = BeanUtils.getValue(entity, property);
            } catch (OgnlException e) {
                result = null;
            }
        }
        result = StringUtils.defaultIfEmpty(result);
        result = BeanUtils.convertValue(result, type);
        return (T) result;
    }

    protected String getJsonStr(JSONObject jsonObject, String key) {
        String result;
        try {
            result = jsonObject.getString(key);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    protected String getJsonStr(JSONObject jsonObject, String key, String defaultValue) {
        String result = getJsonStr(jsonObject, key);
        if (StringUtils.isBlank(result)) {
            result = defaultValue;
        }
        return result;
    }

    protected int getJsonInt(JSONObject jsonObject, String key) {
        int result;
        try {
            result = jsonObject.getInt(key);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

    protected double getJsonDouble(JSONObject jsonObject, String key) {
        double result;
        try {
            result = jsonObject.getDouble(key);
        } catch (Exception e) {
            result = -1.00;
        }
        return result;
    }

    protected void bind(IdEntity entity,String sessionID) throws Exception {
        if (entity instanceof Tracker) {
            if (BeanUtils.getValue(entity, "id") == null) {
                BeanUtils.setValue(entity, "created", DateFormatUtil.getCurrentDate(true));
            }
            BeanUtils.setValue(entity, "updated", DateFormatUtil.getCurrentDate(true));
            try {
                RedisUserInfo redisUserInfo= RedisUserSession.getUserInfo(sessionID);
                if (redisUserInfo != null) {
                    BeanUtils.setValue(entity, "createdBy", redisUserInfo.getUserId());
                    BeanUtils.setValue(entity, "updatedBy", redisUserInfo.getUserId());
                }
            }catch (Exception e){

            }
        }
        try {
            String useYn = BeanUtils.getValue(entity, "useYn", String.class);
            if (StringUtils.isBlank(useYn)) {
                BeanUtils.setValue(entity, "useYn", "N");
            }
        } catch (Exception e) {
            BeanUtils.setValue(entity, "useYn", "N");
        }
    }

    /**
     * 静态方法,注册ApacheConvert策略
     */
    public static void registConverter() {
        ConvertUtils.register(new StringConverter(), String.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new DateConverter(), Date.class);
    }

    static {
        registConverter();
    }
}
