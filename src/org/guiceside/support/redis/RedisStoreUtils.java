package org.guiceside.support.redis;

import org.guiceside.support.properties.PropertiesConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by wangjia on 14-7-14.
 */
public class RedisStoreUtils {
    /**
     * 主机
     */
    /**
     * 端口
     */
    private static final int PORT = 6379;

    static  JedisPool pool=null;

    public static void init(PropertiesConfig webConfig, String dbType) {
        if (pool != null) {
            return;
        }
        if (webConfig != null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //最大空闲连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
            config.setMaxIdle(200);
            //最大连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
            config.setMaxTotal(300);

            config.setTestOnBorrow(false);

            config.setTestWhileIdle(false);

            config.setTestOnReturn(false);
            pool = new JedisPool(config, webConfig.getString(dbType + "_HOST"), PORT, 10000, webConfig.getString(dbType + "_PWD"));
        }
    }

    public static JedisPool getPool() {
        return pool;
    }
    public static void destroy() {
        if (pool != null) {
            pool.destroy();
        }
    }


    public static void set(Jedis jedis, byte[] key, Object serializeObj) {
        jedis.set(key, SerializeUtil.serialize(serializeObj));
    }

    public static void hset(Jedis jedis, byte[] key, byte[] field, Object serializeObj) {
        jedis.hset(key, field, SerializeUtil.serialize(serializeObj));
    }


    public static void del(Jedis jedis, byte[] key) {
        jedis.del(key);
    }

    public static Object hget(Jedis jedis, byte[] key, byte[] field) {
        Object serializeObj = null;
        byte[] serializeByte = null;
        serializeByte = jedis.hget(key, field);
        if (serializeByte != null) {
            serializeObj = SerializeUtil.unserialize(serializeByte);
        }
        return serializeObj;
    }


    public static boolean exists(Jedis jedis, byte[] key) {
        return jedis.exists(key);
    }

    public static Object get(Jedis jedis, byte[] key) {
        Object serializeObj = null;
        byte[] serializeByte = null;
        serializeByte = jedis.get(key);
        if (serializeByte != null) {
            serializeObj = SerializeUtil.unserialize(serializeByte);
        }
        return serializeObj;
    }
}
