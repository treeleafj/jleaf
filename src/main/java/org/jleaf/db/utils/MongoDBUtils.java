package org.jleaf.db.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

public class MongoDBUtils {

    private static Mongo mongo;
    private static DB db;

    /**
     * 初始化
     */
    private static void init() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource("mongodb.properties");
        if (url != null) {
            InputStream in = null;
            try {
                Properties p = new Properties();
                in = url.openStream();
                p.load(in);
                String username = p.getProperty("username");
                String password = p.getProperty("password");
                String ipsStr = p.getProperty("ips");
                String portsStr = p.getProperty("ports");
                String dbname = p.getProperty("dbname");

                List<ServerAddress> serverAddrs = new ArrayList<ServerAddress>();
                ;
                if (StringUtils.isBlank(ipsStr)) {
                    serverAddrs.add(new ServerAddress());
                } else {
                    String[] ips = ipsStr.split(",");
                    String[] ports = portsStr.split(",");
                    for (int i = 0; i < ips.length; i++) {
                        serverAddrs.add(new ServerAddress(ips[i], Integer.parseInt(ports[i])));
                    }
                }

                mongo = new MongoClient(serverAddrs);
                db = mongo.getDB(dbname);

                if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                    boolean b = db.authenticate(username, password.toCharArray());
                    if (!b) {
                        new Error("mongoDB认证失败,用户名或密码错误");
                    }
                }

            } catch (IOException e) {
                throw new Error(e);
            } finally {
                if (in != null) {
                    in.close();
                }
            }

        } else {
            mongo = new MongoClient();
        }
    }

    public static DB getDB(String dbname) {
        if (mongo == null) {
            try {
                init();
            } catch (IOException e) {
                throw new Error(e);
            }
        }
        return mongo.getDB(dbname);
    }

    public static DB getDB() {
        if (mongo == null) {
            try {
                init();
            } catch (IOException e) {
                throw new Error(e);
            }
        }
        return db;
    }

    /**
     * 通过ID查询某一数据,若有多条则返回第一条
     *
     * @param id
     * @param collectionName
     */
    public static DBObject get(Object id, String collectionName) {
        DBCollection collection = MongoDBUtils.getDB().getCollection(collectionName);
        DBObject queryObj = new BasicDBObject();
        queryObj.put("id", id.toString());
        return collection.findOne(queryObj);
    }

    /**
     * 插入一条数据到指定集合
     *
     * @param dbObj
     * @param collectionName
     */
    @SuppressWarnings("unused")
    public static void insert(DBObject dbObj, String collectionName) {
        DBCollection collection = MongoDBUtils.getDB().getCollection(collectionName);
        WriteResult wr = collection.insert(dbObj);
    }

    /**
     * 删除指定对象
     *
     * @param dbObj
     * @param collectionName
     */
    public static void remove(DBObject dbObj, String collectionName) {
        DBCollection collection = MongoDBUtils.getDB().getCollection(collectionName);
        collection.remove(dbObj);
    }

    /**
     * 查询
     *
     * @param limit
     * @param collectionName
     * @return
     */
    public static DBCursor find(DBObject dbObj, int skip, int limit, DBObject sort, String collectionName) {
        DBCollection collection = MongoDBUtils.getDB().getCollection(collectionName);
        DBCursor dbCusrsor = collection.find(dbObj).limit(limit).skip(skip).sort(sort);
        return dbCusrsor;
    }

    /**
     * 修改数据
     *
     * @param query
     * @param dbObj
     * @param collectionName
     */
    public static void update(DBObject query, DBObject dbObj, String collectionName) {
        DBCollection collection = MongoDBUtils.getDB().getCollection(collectionName);
        collection.update(query, dbObj, false, false);
    }
}
