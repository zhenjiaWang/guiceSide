package oa.mingdao.com;

import org.guiceside.support.redis.RedisStoreUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * Created by zhenjiawang on 2016/12/24.
 */
public class TestRedis {

    public static String api(JedisPoolConfig config, JedisPool poolCluster, String cursor) {
        JedisPool poolSource = new JedisPool(config, "139.196.79.168", 56381, 10000, "6kMPJgsV");
        String currentCursor = null;
        Jedis jedisSource = null;
        Jedis jedisCluster = null;
        try {
            jedisSource = poolSource.getResource();
//            jedisCluster = poolCluster.getResource();
//            jedisSource.select(0);
//            jedisCluster.select(0);

//            jedisCluster.pipelined().multi();
//            ScanResult<String> stringScanResult = jedisSource.scan(cursor);
//            currentCursor = stringScanResult.getStringCursor();
//            List<String> keys = stringScanResult.getResult();
//            if (keys != null && !keys.isEmpty()) {
//                for (String k : keys) {
//                    System.out.println("序列号key =" + k);
//                    byte[] b = RedisStoreUtils.dump(jedisSource, k);
//                    if (b != null) {
//                        if (!k.startsWith("API")) {
//                         k="REQ:"+k;
//                        }
//                        RedisStoreUtils.restore(jedisCluster, k, 0, b);
//                        System.out.println("写入key =" + k);
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisSource != null) {
                jedisSource.close();
            }
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }
        return currentCursor;
    }

    public static String asset(JedisPoolConfig config, JedisPool poolCluster, String cursor) {
        JedisPool poolSource = new JedisPool(config, "139.196.100.67", 56380, 10000, "6kMPJgsV");
        String currentCursor = null;
        Jedis jedisSource = null;
        Jedis jedisCluster = null;
        try {
            jedisSource = poolSource.getResource();
            jedisCluster = poolCluster.getResource();
            jedisSource.select(0);
            jedisCluster.select(0);
            ScanResult<String> stringScanResult = jedisSource.scan(cursor);
            currentCursor = stringScanResult.getStringCursor();
            List<String> keys = stringScanResult.getResult();
            if (keys != null && !keys.isEmpty()) {
                for (String k : keys) {
                    System.out.println("序列号key =" + k);
                    byte[] b = RedisStoreUtils.dump(jedisSource, k);
                    if (b != null) {
                        String hashTag = null;
                        if (!k.equals("SYS_ASSET")) {
                            if (k.startsWith("SYS_CNY_ASSET")) {
                                String userId = k.substring("SYS_CNY_ASSET".length());
                                hashTag = "ASSET:CNY:{asset:" + userId + "}";
                            } else if (k.startsWith("SYS_BTC_ASSET")) {
                                String userId = k.substring("SYS_BTC_ASSET".length());
                                hashTag = "ASSET:BTC:{asset:" + userId + "}";
                            } else if (k.startsWith("SYS_COIN_ASSET")) {
                                String userId = k.substring("SYS_COIN_ASSET".length(), k.lastIndexOf("_"));
                                String coinId = k.substring(k.length() - 1);
                                hashTag = "ASSET:COIN:" + coinId + ":{asset:" + userId + "}";
                            } else {
                                hashTag = k;
                            }
                        } else {
                            hashTag = "ASSET:SYS";
                        }
                        RedisStoreUtils.restore(jedisCluster, hashTag, 0, b);
                        System.out.println("写入key =" + k);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisSource != null) {
                jedisSource.close();
            }
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }
        return currentCursor;
    }


    public static String buy(JedisPoolConfig config, JedisPool poolCluster, String cursor) {
        JedisPool poolSource = new JedisPool(config, "123.56.70.244", 59980, 10000, "o2Work2016");
        String currentCursor = null;
        Jedis jedisSource = null;
        Jedis jedisCluster = null;
        try {
            jedisSource = poolSource.getResource();
            jedisCluster = poolCluster.getResource();
            jedisSource.select(1);
            jedisCluster.select(0);
            ScanResult<String> stringScanResult = jedisSource.scan(cursor);
            currentCursor = stringScanResult.getStringCursor();
            List<String> keys = stringScanResult.getResult();
            if (keys != null && !keys.isEmpty()) {
                for (String k : keys) {
                    System.out.println("序列号key =" + k);
                    byte[] b = RedisStoreUtils.dump(jedisSource, k);
                    if (b != null) {
                        String hashTag = "ex:buy:" + k + ":{1:CNY}";
                        RedisStoreUtils.restore(jedisCluster, hashTag, 0, b);
                        System.out.println("写入key =" + k);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisSource != null) {
                jedisSource.close();
            }
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }
        return currentCursor;
    }


    public static String sell(JedisPoolConfig config, JedisPool poolCluster, String cursor) {
        JedisPool poolSource = new JedisPool(config, "123.56.70.244", 59980, 10000, "o2Work2016");
        String currentCursor = null;
        Jedis jedisSource = null;
        Jedis jedisCluster = null;
        try {
            jedisSource = poolSource.getResource();
            jedisCluster = poolCluster.getResource();
            jedisSource.select(2);
            jedisCluster.select(0);
            ScanResult<String> stringScanResult = jedisSource.scan(cursor);
            currentCursor = stringScanResult.getStringCursor();
            List<String> keys = stringScanResult.getResult();
            if (keys != null && !keys.isEmpty()) {
                for (String k : keys) {
                    System.out.println("序列号key =" + k);
                    byte[] b = RedisStoreUtils.dump(jedisSource, k);
                    if (b != null) {
                        String hashTag = "ex:sell:" + k + ":{1:CNY}";
                        RedisStoreUtils.restore(jedisCluster, hashTag, 0, b);
                        System.out.println("写入key =" + k);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisSource != null) {
                jedisSource.close();
            }
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }
        return currentCursor;
    }


    public static String deal(JedisPoolConfig config, JedisPool poolCluster, String cursor) {
        JedisPool poolSource = new JedisPool(config, "123.56.70.244", 59980, 10000, "o2Work2016");
        String currentCursor = null;
        Jedis jedisSource = null;
        Jedis jedisCluster = null;
        try {
            jedisSource = poolSource.getResource();
            jedisCluster = poolCluster.getResource();
            jedisSource.select(3);
            jedisCluster.select(0);
            ScanResult<String> stringScanResult = jedisSource.scan(cursor);
            currentCursor = stringScanResult.getStringCursor();
            List<String> keys = stringScanResult.getResult();
            if (keys != null && !keys.isEmpty()) {
                for (String k : keys) {
                    System.out.println("序列号key =" + k);
                    byte[] b = RedisStoreUtils.dump(jedisSource, k);
                    if (b != null) {
                        String hashTag = "ex:deal:" + k + ":{1:CNY}";
                        RedisStoreUtils.restore(jedisCluster, hashTag, 0, b);
                        System.out.println("写入key =" + k);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisSource != null) {
                jedisSource.close();
            }
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }
        return currentCursor;
    }

    public static void redis() {
        JedisPoolConfig config = new JedisPoolConfig();
        //最大空闲连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
        config.setMaxIdle(200);
        //最大连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
        config.setMaxTotal(300);

        config.setTestOnBorrow(false);

        config.setTestWhileIdle(false);

        config.setTestOnReturn(false);


        final JedisPool poolCluster = new JedisPool(config, "139.196.79.168", 56380, 10000, "6kMPJgsV");
//        String cursor = api(config, poolCluster, "0");
//        while (!cursor.equals("0")) {
//            cursor = api(config, poolCluster, cursor);
//        }
        String cursor = asset(config, poolCluster, "0");
        while (!cursor.equals("0")) {
            cursor = asset(config, poolCluster, cursor);
        }

//        String cursor = buy(config, poolCluster, "0");
//        while (!cursor.equals("0")) {
//            cursor = buy(config, poolCluster, cursor);
//        }

//        String cursor = sell(config, poolCluster, "0");
//        while (!cursor.equals("0")) {
//            cursor = sell(config, poolCluster, cursor);
//        }

//        String cursor = deal(config, poolCluster, "0");
//        while (!cursor.equals("0")) {
//            cursor = deal(config, poolCluster, cursor);
//        }
    }

    public static void main(String args[]) throws Exception {
        //String a = "SYS_COIN_ASSET6237489035631771648_1";
        //System.out.println(a.substring(a.length() - 1));
        JedisPoolConfig config = new JedisPoolConfig();
        //最大空闲连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
        config.setMaxIdle(200);
        //最大连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
        config.setMaxTotal(300);

        config.setTestOnBorrow(false);

        config.setTestWhileIdle(false);

        config.setTestOnReturn(false);


        final JedisPool poolCluster = new JedisPool(config, "123.56.70.244", 56379, 10000, "6kMPJgsV");
         poolCluster.getResource();
    }
}
