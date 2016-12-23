package org.guiceside.support.redis;

import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangjia on 14-7-14.
 */
public class RedisPoolProvider {
    /**
     * 主机
     */
    /**
     * 端口
     */

    static ConcurrentHashMap<String, JedisPool> mapPool = null;

    public static void init(PropertiesConfig webConfig) {
        if (mapPool != null) {
            return;
        }
        if (webConfig != null) {
            String redisDB = webConfig.getString("redisDB");
            String releaseEnvironment = webConfig.getString("releaseEnvironment");
            if (StringUtils.isNotBlank(redisDB) && StringUtils.isNotBlank(releaseEnvironment)) {
                String[] dbs = redisDB.split(",");
                if (dbs != null && dbs.length > 0) {
                    mapPool = new ConcurrentHashMap<>();
                    for (String dbName : dbs) {
                        JedisPoolConfig config = new JedisPoolConfig();
                        //最大空闲连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
                        config.setMaxIdle(200);
                        //最大连接数, 应用自己评估，不要超过KVStore每个实例最大的连接数
                        config.setMaxTotal(300);

                        config.setTestOnBorrow(false);

                        config.setTestWhileIdle(false);

                        config.setTestOnReturn(false);

                        String host = webConfig.getString(releaseEnvironment + "_" + dbName + "_HOST");
                        String pwd = webConfig.getString(releaseEnvironment + "_" + dbName + "_HOST");
                        int port = webConfig.getInt(releaseEnvironment + "_" + dbName + "_PORT");
                        JedisPool pool = new JedisPool(config, host, port, 10000, pwd);
                        mapPool.put(dbName, pool);
                    }
                }
            }
        }
    }

    public static JedisPool getPool(String dbName) {
        if (mapPool != null) {
            return mapPool.get(dbName);
        }
        return null;
    }

    public static void destroy(String dbName) {
        if (mapPool != null) {
            JedisPool pool = mapPool.get(dbName);
            if (pool != null) {
                pool.destroy();
            }
        }
    }
}
