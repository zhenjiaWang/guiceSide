package org.guiceside.support.mongdb;

import com.mongodb.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjia on 16/6/15.
 */
public class MongoManager {

    private static MongoClient client;

    private static String dbName;

    private static final ThreadLocal<Jongo> jongoThreadLocal = new ThreadLocal<Jongo>();

    private static final ThreadLocal<Map<String, MongoCollection>> mongoCollectionThreadLocal = new ThreadLocal<Map<String, MongoCollection>>();



    private MongoManager() {
    }

    public static void init(PropertiesConfig webConfig) {
        String releaseEnvironment=webConfig.getString("releaseEnvironment");
        if(StringUtils.isNotBlank(releaseEnvironment)){
            String serverName=releaseEnvironment+"_";
            if (client != null) return;
            String userName = webConfig.getString(serverName+"MONGO_userName");
            String password = webConfig.getString(serverName+"MONGO_password");
            String defaultDB = webConfig.getString(serverName+"MONGO_defaultDb");
            dbName = defaultDB;
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            builder.connectionsPerHost(webConfig.getInt(serverName+"MONGO_connections_per_host"));
            builder.maxWaitTime(webConfig.getInt(serverName+"MONGO_max_wait_time"));
            builder.socketTimeout(webConfig.getInt(serverName+"MONGO_socket_timeout"));
            builder.connectTimeout(webConfig.getInt(serverName+"MONGO_connect_timeout"));
            builder.threadsAllowedToBlockForConnectionMultiplier(webConfig.getInt(serverName+"MONGO_threads_allowed_to_block_for_connection_multiplier"));
            String host1 = webConfig.getString(serverName+"MONGO_host1");
            String host2 = webConfig.getString(serverName+"MONGO_host2");
            Integer port = webConfig.getInt(serverName+"MONGO_port");
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
            System.out.println(serverName);
            System.out.println(userName);
            System.out.println(password);
            System.out.println(defaultDB);
            System.out.println(host1);
            System.out.println(host2);
            client = new MongoClient(uri);
        }
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
