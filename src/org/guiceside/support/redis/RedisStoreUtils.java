package org.guiceside.support.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by wangjia on 14-7-14.
 */
public class RedisStoreUtils {
    public static void expire(Jedis jedis, byte[] key, int seconds) {
        jedis.expire(key, seconds);
    }

    public static void persist(Jedis jedis, byte[] key) {
        jedis.persist(key);
    }

    public static void del(Jedis jedis, byte[] key) {
        jedis.del(key);
    }

    public static boolean exists(Jedis jedis, byte[] key) {
        return jedis.exists(key);
    }


    public static void set(Jedis jedis, byte[] key, Object serializeObj) {
        jedis.set(key, SerializeUtil.serialize(serializeObj));
    }

    public static void hset(Jedis jedis, byte[] key, byte[] field, Object serializeObj) {
        jedis.hset(key, field, SerializeUtil.serialize(serializeObj));
    }

    public static void hdel(Jedis jedis, byte[] key, byte[] field) {
        jedis.hdel(key, field);
    }

    public static void hmset(Jedis jedis, byte[] key, Map<byte[], byte[]> map) {
        jedis.hmset(key, map);
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


    public static boolean setbit(Jedis jedis, byte[] key, long offset, boolean value) {
        return jedis.setbit(key, offset, value);
    }

    public static boolean setbit(Jedis jedis, byte[] key, long offset, byte[] value) {
        return jedis.setbit(key, offset, value);
    }

    public static boolean getbit(Jedis jedis, byte[] key, long offset) {
        return jedis.getbit(key, offset);
    }

    public static long bitcount(Jedis jedis, byte[] key) {
        return jedis.bitcount(key);
    }

    public static long bitcount(Jedis jedis, byte[] key, long start, long end) {
        return jedis.bitcount(key, start, end);
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
