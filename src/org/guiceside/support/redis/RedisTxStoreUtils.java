package org.guiceside.support.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wangjia on 14-7-14.
 */
public class RedisTxStoreUtils {
    public static void expire(Transaction jedis, byte[] key, int seconds) {
        jedis.expire(key, seconds);
    }

    public static void expire(Transaction jedis, String key, int seconds) {
        jedis.expire(key, seconds);
    }

    public static void persist(Transaction jedis, byte[] key) {
        jedis.persist(key);
    }

    public static Response<Double> incrByFloat(Transaction jedis, String key, double number) {
       return jedis.incrByFloat(key, number);
    }

    public static void del(Transaction jedis, byte[] key) {
        jedis.del(key);
    }

    public static void del(Transaction jedis, String key) {
        jedis.del(key);
    }


    public static void set(Transaction jedis, byte[] key, Serializable serializeObj) {
        jedis.set(key, SerializeUtil.serialize(serializeObj));
    }

    public static void set(Transaction jedis, String key, String value) {
        jedis.set(key, value);
    }


    public static void hset(Transaction jedis, byte[] key, byte[] field, Serializable serializeObj) {
        jedis.hset(key, field, SerializeUtil.serialize(serializeObj));
    }


    public static void hset(Transaction jedis, String key, String field, String value) {
        jedis.hset(key, field, value);
    }

    public static Response<Double> hincrByFloat(Transaction jedis, String key, String field, double number) {
       return jedis.hincrByFloat(key, field, number);
    }

    public static void hdel(Transaction jedis, byte[] key, byte[] field) {
        jedis.hdel(key, field);
    }

    public static void hdel(Transaction jedis, String key, String field) {
        jedis.hdel(key, field);
    }

    public static void hmset(Transaction jedis, byte[] key, Map<byte[], byte[]> map) {
        jedis.hmset(key, map);
    }

    public static void hmset(Transaction jedis, String key, Map<String, String> map) {
        jedis.hmset(key, map);
    }

    public static void sadd(Transaction jedis, String key, String member) {
        jedis.sadd(key, member);
    }

    public static void zadd(Transaction jedis, String key, double score, String member) {
        jedis.zadd(key, score, member);
    }

    public static void zincrby(Transaction jedis, String key, double score, String member) {
        jedis.zincrby(key, score, member);
    }

    public static void zrem(Transaction jedis, String key, String member) {
        jedis.zrem(key, member);
    }


    public static void ltrim(Transaction jedis, String key, long start, long stop) {
        jedis.ltrim(key, start, stop);
    }

    public static void ltrim(Transaction jedis, byte[] key, long start, long stop) {
        jedis.ltrim(key, start, stop);
    }

    public static void lpush(Transaction jedis, String key, String value) {
        jedis.lpush(key, value);
    }

    public static void lpush(Transaction jedis, byte[] key, Serializable serializeObj) {
        jedis.lpush(key, SerializeUtil.serialize(serializeObj));
    }

    public static void lrem(Transaction jedis, int count, String key,String value) {
        jedis.lrem(key, count, value);
    }
}
