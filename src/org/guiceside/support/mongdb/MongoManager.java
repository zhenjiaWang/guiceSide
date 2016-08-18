package org.guiceside.support.mongdb;

import com.mongodb.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;
import org.hibernate.Session;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wangjia on 16/6/15.
 */
public class MongoManager {

    private static MongoClient client;

    private static String dbName;

    private static final ThreadLocal<Jongo> jongoThreadLocal = new ThreadLocal<Jongo>();

    private static final ThreadLocal<Map<String, MongoCollection>> mongoCollectionThreadLocal = new ThreadLocal<Map<String, MongoCollection>>();

    static {
        InputStream in = MongoManager.class.getClassLoader().getResourceAsStream("webconfig.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private MongoManager() {
    }

    public static void init(PropertiesConfig webConfig) {
        if (client != null) return;
        String userName = webConfig.getString("MONGO_userName");
        String password = webConfig.getString("MONGO_password");
        String defaultDB = webConfig.getString("MONGO_defaultDb");
        dbName = defaultDB;
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(webConfig.getInt("MONGO_connections_per_host"));
        builder.maxWaitTime(webConfig.getInt("MONGO_max_wait_time"));
        builder.socketTimeout(webConfig.getInt("MONGO_socket_timeout"));
        builder.connectTimeout(webConfig.getInt("MONGO_connect_timeout"));
        builder.threadsAllowedToBlockForConnectionMultiplier(webConfig.getInt("MONGO_threads_allowed_to_block_for_connection_multiplier"));
        String host1 = webConfig.getString("MONGO_host1");
        String host2 = webConfig.getString("MONGO_host2");
        Integer port = webConfig.getInt("MONGO_port");
        MongoClientURI uri = null;
        if (StringUtils.isNotBlank(host1) && StringUtils.isNotBlank(host2)) {
            ServerAddress seed1 = new ServerAddress(host1, port);
            ServerAddress seed2 = new ServerAddress(host2, port);

            String replicaSet = "mgset-1296367";

            uri = new MongoClientURI("mongodb://" + userName + ":" + password + "@" + seed1 + "," + seed2 + "/" + defaultDB + "?replicaSet=" + replicaSet, builder);
        } else if (StringUtils.isNotBlank(host1)) {
            ServerAddress sa = new ServerAddress(host1, port);
            uri = new MongoClientURI("mongodb://" + userName + ":" + password + "@" + sa + "/" + defaultDB, builder);
        }
        client = new MongoClient(uri);
    }

    public static void shutdown() {
        if (client != null) {
            client.close();
        }
    }

    private static DB getDB() {
        return client.getDB(dbName);
    }

    private static MongoCollection getCollection(String tableName) {
        Jongo jongo = jongoThreadLocal.get();
        if (jongo == null) {
            jongo = new Jongo(getDB());
            jongoThreadLocal.set(jongo);
        }
        Map<String, MongoCollection> map = mongoCollectionThreadLocal.get();
        if (map == null) {
            map = new HashMap<String, MongoCollection>();
        }
        if (!map.containsKey(tableName)) {
            MongoCollection mongoCollection = jongo.getCollection(tableName);
            map.put(tableName, mongoCollection);
            mongoCollectionThreadLocal.set(map);
        }
        return map.get(tableName);
    }

    public static JSONObject findOne(String tableName, String query) {
        return getCollection(tableName).findOne(query).as(JSONObject.class);
    }

    public static JSONArray find(String tableName, String query) {
        JSONArray jsonArray = null;
        MongoCursor<JSONObject> mongoCursor = getCollection(tableName).find(query).as(JSONObject.class);
        if (mongoCursor != null) {
            jsonArray = new JSONArray();
            for (JSONObject jsonObject : mongoCursor) {
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    public static void insert(String tableName, String obj) {
        getCollection(tableName).insert(obj);
    }

    public static void update(String tableName, String query, String newObj) {
        getCollection(tableName).update(query).with(newObj);
    }

    public static void updateMulti(String tableName, String query, String newObj) {
        getCollection(tableName).update(query).multi().with(newObj);
    }

    public static void remove(String tableName, String query) {
        getCollection(tableName).remove(query);
    }
}
