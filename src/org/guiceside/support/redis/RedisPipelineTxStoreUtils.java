package org.guiceside.support.redis;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wangjia on 14-7-14.
 */
public class RedisPipelineTxStoreUtils {
    public static void expire(Pipeline jedis, byte[] key, int seconds) {
        jedis.expire(key, seconds);
    }

    public static void expire(Pipeline jedis, String key, int seconds) {
        jedis.expire(key, seconds);
    }

    public static Response<Boolean> exists(Pipeline jedis, String key) {
        return jedis.exists(key);
    }

    public static void persist(Pipeline jedis, byte[] key) {
        jedis.persist(key);
    }

    public static Response<Double> incrByFloat(Pipeline jedis, String key, double number) {
        return jedis.incrByFloat(key, number);
    }

    public static Response<Long> incrBy(Pipeline jedis, String key, long diff) {
        return jedis.incrBy(key, diff);
    }

    public static void del(Pipeline jedis, byte[] key) {
        jedis.del(key);
    }

    public static void del(Pipeline jedis, String key) {
        jedis.del(key);
    }


    public static void set(Pipeline jedis, byte[] key, Serializable serializeObj) {
        jedis.set(key, SerializeUtil.serialize(serializeObj));
    }

    public static void set(Pipeline jedis, String key, String value) {
        jedis.set(key, value);
    }


    public static void hset(Pipeline jedis, byte[] key, byte[] field, Serializable serializeObj) {
        jedis.hset(key, field, SerializeUtil.serialize(serializeObj));
    }


    public static void hset(Pipeline jedis, String key, String field, String value) {
        jedis.hset(key, field, value);
    }

    public static Response<Double> hincrByFloat(Pipeline jedis, String key, String field, double number) {
        return jedis.hincrByFloat(key, field, number);
    }

    public static Response<Long> hincrBy(Pipeline jedis, String key, String field, long number) {
        return jedis.hincrBy(key, field, number);
    }

    public static void hdel(Pipeline jedis, byte[] key, byte[] field) {
        jedis.hdel(key, field);
    }

    public static void hdel(Pipeline jedis, String key, String field) {
        jedis.hdel(key, field);
    }

    public static void hmset(Pipeline jedis, byte[] key, Map<byte[], byte[]> map) {
        jedis.hmset(key, map);
    }

    public static void hmset(Pipeline jedis, String key, Map<String, String> map) {
        jedis.hmset(key, map);
    }

    public static void sadd(Pipeline jedis, String key, String member) {
        jedis.sadd(key, member);
    }

    public static void zadd(Pipeline jedis, String key, double score, String member) {
        jedis.zadd(key, score, member);
    }

    public static void zincrby(Pipeline jedis, String key, double score, String member) {
        jedis.zincrby(key, score, member);
    }

    public static void zrem(Pipeline jedis, String key, String member) {
        jedis.zrem(key, member);
    }


    public static void ltrim(Pipeline jedis, String key, long start, long stop) {
        jedis.ltrim(key, start, stop);
    }

    public static void ltrim(Pipeline jedis, byte[] key, long start, long stop) {
        jedis.ltrim(key, start, stop);
    }

    public static void lpush(Pipeline jedis, String key, String value) {
        jedis.lpush(key, value);
    }

    public static void lpush(Pipeline jedis, byte[] key, Serializable serializeObj) {
        jedis.lpush(key, SerializeUtil.serialize(serializeObj));
    }

    public static void lrem(Pipeline jedis, int count, String key, String value) {
        jedis.lrem(key, count, value);
    }
}
