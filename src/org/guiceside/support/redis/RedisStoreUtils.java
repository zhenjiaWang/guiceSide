package org.guiceside.support.redis;

import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.NumberUtils;
import org.guiceside.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.io.Serializable;
import java.util.*;

/**
 * Created by wangjia on 14-7-14.
 */
public class RedisStoreUtils {
    public static void expire(Jedis jedis, byte[] key, int seconds) {
        jedis.expire(key, seconds);
    }

    public static void expire(Jedis jedis, String key, int seconds) {
        jedis.expire(key, seconds);
    }

    public static long incr(Jedis jedis, String key) {
        return jedis.incr(key);
    }

    public static long incrBy(Jedis jedis, String key, long diff) {
        return jedis.incrBy(key, diff);
    }

    public static void persist(Jedis jedis, byte[] key) {
        jedis.persist(key);
    }

    public static double incrByFloat(Jedis jedis, String key, double number) {
        return jedis.incrByFloat(key, number);
    }

    public static void del(Jedis jedis, byte[] key) {
        jedis.del(key);
    }

    public static void del(Jedis jedis, String key) {
        jedis.del(key);
    }

    public static boolean exists(Jedis jedis, byte[] key) {
        return jedis.exists(key);
    }

    public static boolean exists(Jedis jedis, String key) {
        return jedis.exists(key);
    }

    public static void set(Jedis jedis, byte[] key, Serializable serializeObj) {
        jedis.set(key, SerializeUtil.serialize(serializeObj));
    }

    public static ScanResult<String> set(Jedis jedis, String cursor) {
        return jedis.scan(cursor);
    }

    public static void restore(Jedis jedis, String key, int ttl, byte[] serializeByte) {
        jedis.restore(key, ttl, serializeByte);
    }

    public static byte[] dump(Jedis jedis, String key) {
        return jedis.dump(key);
    }

    public static void set(Jedis jedis, String key, String value) {
        jedis.set(key, value);
    }

    public static boolean hexists(Jedis jedis, String key, String field) {
        return jedis.hexists(key, field);
    }

    public static void hset(Jedis jedis, byte[] key, byte[] field, Serializable serializeObj) {
        jedis.hset(key, field, SerializeUtil.serialize(serializeObj));
    }


    public static void hset(Jedis jedis, String key, String field, String value) {
        jedis.hset(key, field, value);
    }

    public static double hincrByFloat(Jedis jedis, String key, String field, double number) {
        return jedis.hincrByFloat(key, field, number);
    }

    public static long hincrBy(Jedis jedis, String key, String field, long number) {
        return jedis.hincrBy(key, field, number);
    }

    public static void hdel(Jedis jedis, byte[] key, byte[] field) {
        jedis.hdel(key, field);
    }

    public static void hdel(Jedis jedis, String key, String field) {
        jedis.hdel(key, field);
    }

    public static void hmset(Jedis jedis, byte[] key, Map<byte[], byte[]> map) {
        jedis.hmset(key, map);
    }

    public static void hmset(Jedis jedis, String key, Map<String, String> map) {
        jedis.hmset(key, map);
    }


    public static Serializable hget(Jedis jedis, byte[] key, byte[] field) {
        Serializable serializeObj = null;
        byte[] serializeByte = null;
        serializeByte = jedis.hget(key, field);
        if (serializeByte != null) {
            serializeObj = byte2Obj(serializeByte);
        }
        return serializeObj;
    }

    public static String hgetString(Jedis jedis, String key, String field) {
        return jedis.hget(key, field);
    }

    public static double hgetDouble(Jedis jedis, String key, String field) {
        Double resultDouble = null;
        String resultStr = jedis.hget(key, field);
        if (StringUtils.isNotBlank(resultStr)) {
            resultDouble = BeanUtils.convertValue(resultStr, Double.class);
        }
        if (resultDouble == null) {
            resultDouble = 0.00d;
        }
        return resultDouble.doubleValue();
    }

    public static double hgetDouble(Jedis jedis, String key, String field, int scale) {
        Double resultDouble = null;
        String resultStr = jedis.hget(key, field);
        if (StringUtils.isNotBlank(resultStr)) {
            resultDouble = BeanUtils.convertValue(resultStr, Double.class);
        }
        if (resultDouble == null) {
            resultDouble = 0.00d;
        }
        resultDouble = NumberUtils.multiply(resultDouble, 1, scale);
        return resultDouble.doubleValue();
    }

    public static Integer hgetInteger(Jedis jedis, String key, String field) {
        Integer i = null;
        String integerStr = jedis.hget(key, field);
        if (StringUtils.isNotBlank(integerStr)) {
            i = BeanUtils.convertValue(integerStr, Integer.class);
        }
        if (i == null) {
            i = -1;
        }
        return i;
    }

    public static long hgetLong(Jedis jedis, String key, String field) {
        Long resultLong = null;
        String resultStr = jedis.hget(key, field);
        if (StringUtils.isNotBlank(resultStr)) {
            resultLong = BeanUtils.convertValue(resultStr, Long.class);
        }
        if (resultLong == null) {
            resultLong = 0l;
        }
        return resultLong.longValue();
    }

    public static void setbit(Jedis jedis, byte[] key, int offset, boolean value) {
        BitSet bitSet = null;
        if (jedis.exists(key)) {
            Serializable bitSerializable = get(jedis, key);
            if (bitSerializable != null) {
                bitSet = (BitSet) bitSerializable;
            }
        } else {
            bitSet = new BitSet();
        }
        bitSet.set(offset, value);
        if (bitSet.cardinality() > 0) {
            set(jedis, key, bitSet);
        } else {
            del(jedis, key);
        }

    }


    public static BitSet getbit(Jedis jedis, byte[] key) {
        if (jedis.exists(key)) {
            Serializable bitSerializable = get(jedis, key);
            if (bitSerializable != null) {
                BitSet bitSet = (BitSet) bitSerializable;
                return bitSet;
            }
        }
        return null;
    }

    public static Double getDouble(Jedis jedis, String key, int scale) {
        Double d = null;
        String doubleStr = jedis.get(key);
        if (StringUtils.isNotBlank(doubleStr)) {
            d = BeanUtils.convertValue(doubleStr, Double.class);
            if (d != null) {
                d = NumberUtils.multiply(d, 1, scale);
            }
        }
        if (d == null) {
            d = 0.00d;
        }
        return d;
    }

    public static Long getLong(Jedis jedis, String key) {
        Long l = null;
        String longStr = jedis.get(key);
        if (StringUtils.isNotBlank(longStr)) {
            l = BeanUtils.convertValue(longStr, Long.class);
        }
        if (l == null) {
            l = 0l;
        }
        return l;
    }


    public static Integer getInteger(Jedis jedis, String key) {
        Integer i = null;
        String integerStr = jedis.get(key);
        if (StringUtils.isNotBlank(integerStr)) {
            i = BeanUtils.convertValue(integerStr, Integer.class);
        }
        if (i == null) {
            i = -1;
        }
        return i;
    }

    public static String getString(Jedis jedis, String key) {
        return jedis.get(key);
    }

    public static Serializable get(Jedis jedis, byte[] key) {
        Serializable serializeObj = null;
        byte[] serializeByte = null;
        serializeByte = jedis.get(key);
        if (serializeByte != null) {
            serializeObj = byte2Obj(serializeByte);
        }
        return serializeObj;
    }

    public static byte[] getByte(Jedis jedis, byte[] key) {
        byte[] serializeByte = jedis.get(key);
        return serializeByte;
    }

    public static void sadd(Jedis jedis, String key, String member) {
        jedis.sadd(key, member);
    }

    public static void srem(Jedis jedis, String key, String member) {
        jedis.srem(key, member);
    }

    public static long scard(Jedis jedis, String key) {
        return jedis.scard(key);
    }

    public static boolean sismember(Jedis jedis, String key, String member) {
        return jedis.sismember(key, member);
    }

    public static void zadd(Jedis jedis, String key, double score, String member) {
        jedis.zadd(key, score, member);
    }

    public static void zincrby(Jedis jedis, String key, double score, String member) {
        jedis.zincrby(key, score, member);
        double resultScore = zscore(jedis, key, member);
        if (resultScore <= 0) {
            zrem(jedis, key, member);
        }
    }

    public static double zscore(Jedis jedis, String key, String member) {
        return jedis.zscore(key, member);
    }

    public static void zrem(Jedis jedis, String key, String member) {
        jedis.zrem(key, member);
    }

    public static Long zrevrank(Jedis jedis, String key, String member) {
        return jedis.zrevrank(key, member);
    }

    public static Set<String> zrevrange(Jedis jedis, String key, long start, long stop) {
        return jedis.zrevrange(key, start, stop);
    }

    public static Set<Tuple> zrevrangeByScoreWithScores(Jedis jedis, String key, double max, double min) {
        return jedis.zrevrangeByScoreWithScores(key, max, min);
    }

    public static Long zrank(Jedis jedis, String key, String member) {
        return jedis.zrank(key, member);
    }

    public static Set<String> zrange(Jedis jedis, String key, long start, long stop) {
        return jedis.zrange(key, start, stop);
    }

    public static Set<Tuple> zrangeByScoreWithScores(Jedis jedis, String key, double min, double max) {
        return jedis.zrangeByScoreWithScores(key, min, max);
    }

    public static Long zcard(Jedis jedis, String key) {
        return jedis.zcard(key);
    }

    public static long lpush(Jedis jedis, String key, String value) {
        return jedis.lpush(key, value);
    }

    public static long lpush(Jedis jedis, byte[] key, Serializable serializeObj) {
        return jedis.lpush(key, SerializeUtil.serialize(serializeObj));
    }

    public static String ltrim(Jedis jedis, String key, long start, long stop) {
        return jedis.ltrim(key, start, stop);
    }

    public static String ltrim(Jedis jedis, byte[] key, long start, long stop) {
        return jedis.ltrim(key, start, stop);
    }

    public static List<String> lrange(Jedis jedis, String key, long start, long stop) {
        List<String> list = jedis.lrange(key, start, stop);
        return list;
    }


    public static List<Serializable> lrange(Jedis jedis, byte[] key, long start, long stop) {
        List<byte[]> list = jedis.lrange(key, start, stop);
        List<Serializable> serializableList = null;
        if (list != null && !list.isEmpty()) {
            serializableList = new ArrayList<>();
            for (byte[] serializeByte : list) {
                Serializable serializable = byte2Obj(serializeByte);
                if (serializable != null) {
                    serializableList.add(serializable);
                }
            }
        }
        return serializableList;
    }

    public static long llen(Jedis jedis, String key) {
        return jedis.llen(key);
    }

    public static long llen(Jedis jedis, byte[] key) {
        return jedis.llen(key);
    }

    public static void lrem(Jedis jedis, int count, String key, String value) {
        jedis.lrem(key, count, value);
    }

    public static String rpop(Jedis jedis, String key) {
        return jedis.rpop(key);
    }

    private static Serializable byte2Obj(byte[] serializeByte) {
        Serializable serializeObj = SerializeUtil.unserialize(serializeByte);
        return serializeObj;
    }

}
