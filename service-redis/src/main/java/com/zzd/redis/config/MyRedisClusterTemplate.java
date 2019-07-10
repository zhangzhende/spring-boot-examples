

package com.zzd.redis.config;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import redis.clients.jedis.JedisCluster;


//@Component
public class MyRedisClusterTemplate {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRedisClusterTemplate.class);

    /**
     * jedis对象
     */
//    @Autowired
    private JedisCluster jedis;

    /**
     * 设置缓存
     * 
     * @param key
     *            缓存key
     * @param value
     *            缓存value
     */
    public void set(String key, String value) {
        jedis.set(key, value);
        LOGGER.debug("RedisUtil:set cache key={},value={}", key, value);
    }

    /**
     * 设置缓存并设置超时时间
     * 
     * @param key
     *            缓存key
     * @param value
     *            缓存value
     * @param expireTime
     *            过期时间(单位：秒)
     */
    public void set(String key, String value, int expireTime) {
        jedis.setex(key, expireTime, value);
        LOGGER.debug("RedisUtil:set cache key={},value={},expireTime={}", key, value, expireTime);
    }

    /**
     * 获取指定key的缓存
     * 
     * @param key
     * @return 返回指定key的value
     */
    public String get(String key) {
        String value = jedis.get(key);
        LOGGER.debug("RedisUtil:get cache key={},value={}", key, value);
        return value;
    }

    /**
     * 设置 list Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param key
     *            key
     * @param list
     *            list对象
     * @see
     */
    public <T> void setList(String key, List<T> list) {
        try {
            jedis.set(key.getBytes(), SerializationUtil.serialize(list));
        }
        catch (Exception e) {
            LOGGER.error("setList key error key: " + key + " error:" + e);
        }
    }

    /**
     * 设置 list Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param key
     *            key
     * @param list
     *            list对象
     * @param expireTime
     *            超时时间(单位：秒)
     */
    public <T> void setList(String key, List<T> list, int expireTime) {
        try {
            jedis.setex(key.getBytes(), expireTime, SerializationUtil.serialize(list));
        }
        catch (Exception e) {
            LOGGER.error("setList key error key: " + key + " error:" + e);
        }
    }

    /**
     * 设置 list Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param key
     *            key
     * @param list
     *            LinkedList对象
     * @see
     */
    public <T> void setLinkedList(String key, LinkedList<T> list) {
        try {
            jedis.set(key.getBytes(), SerializationUtil.serialize(list));
        }
        catch (Exception e) {
            LOGGER.error("setList key error key: " + key + " error:" + e);
        }
    }

    /**
     * 设置 list
     * 
     * @param key
     *            key
     * @param list
     *            LinkedList对象
     * @param expireTime
     *            超时时间(单位：秒)
     */
    public <T> void setLinkedList(String key, LinkedList<T> list, int expireTime) {
        try {
            jedis.setex(key.getBytes(), expireTime, SerializationUtil.serialize(list));
        }
        catch (Exception e) {
            LOGGER.error("setList key error key: " + key + " error:" + e);
        }
    }

    /**
     * 获取list
     * 
     * @param key
     *            key
     * @return list 返回list对象
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String key) {
        try {
            byte[] in = jedis.get(key.getBytes());
            List<T> list = (List<T>)SerializationUtil.deserialize(in);
            return list;
        }
        catch (Exception e) {
            LOGGER.error("getList key error key: " + key + " error:" + e);
            return null;
        }

    }

    /**
     * 获取LinkedList
     * 
     * @param key
     *            key
     * @return list 返回list对象
     */
    @SuppressWarnings("unchecked")
    public <T> LinkedList<T> getLinkedList(String key) {
        try {
            byte[] in = jedis.get(key.getBytes());
            LinkedList<T> list = (LinkedList<T>)SerializationUtil.deserialize(in);
            return list;
        }
        catch (Exception e) {
            LOGGER.error("getList key error key: " + key + " error:" + e);
            return null;
        }

    }

    /**
     * 设置 map
     * 
     * @param key
     *            key
     * @param map
     *            map对象
     */
    public <T> void setMap(String key, Map<String, T> map) {
        try {
            jedis.set(key.getBytes(), SerializationUtil.serialize(map));
        }
        catch (Exception e) {
            LOGGER.error("setMap key error key: " + key + " error:" + e);
        }
    }

    /**
     * 设置 map
     * 
     * @param key
     *            key
     * @param map
     *            map对象
     * @param expireTime
     *            超时时间(单位：秒)
     */
    public <T> void setMap(String key, Map<String, T> map, int expireTime) {
        try {
            jedis.setex(key.getBytes(), expireTime, SerializationUtil.serialize(map));
        }
        catch (Exception e) {
            LOGGER.error("setMap key error key: " + key + " error:" + e);
        }
    }

    /**
     * 获取Map
     * 
     * @param key
     *            key
     * @return 返回map对象
     */
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getMap(String key) {
        try {
            byte[] in = jedis.get(key.getBytes());
            Map<String, T> map = (Map<String, T>)SerializationUtil.deserialize(in);
            return map;
        }
        catch (Exception e) {
            LOGGER.error("getMap key error key: " + key + " error:" + e);
            return null;
        }

    }

    /**
     * 获取Map
     * 
     * @param key
     *            取map的key
     * @param skey
     *            取map里面数据的key
     * @return
     * @return 返回map对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getMap(String key, String skey) {
        try {
            byte[] in = jedis.get(key.getBytes());
            Map<String, T> map = (Map<String, T>)SerializationUtil.deserialize(in);
            T t = map.get(skey);
            map = null;
            return t;
        }
        catch (Exception e) {
            LOGGER.error("getMap key error key: " + key + " error:" + e);
            return null;
        }

    }

    /**
     * 设置 object
     * 
     * @param key
     *            key
     * @param obj
     *            object对象
     */
    public <T> void setObject(String key, Object obj) {
        try {
            jedis.set(key.getBytes(), SerializationUtil.serialize(obj));
        }
        catch (Exception e) {
            LOGGER.error("setObject key error : " + e);
        }
    }

    /**
     * 设置 object并设置超时时间
     * 
     * @param key
     *            key
     * @param obj
     *            object对象
     * @param expireTime
     *            超时时间(单位：秒)
     */
    public <T> void setObject(String key, Object obj, int expireTime) {
        try {
            jedis.setex(key.getBytes(), expireTime, SerializationUtil.serialize(obj));
        }
        catch (Exception e) {
            LOGGER.error("setObject key error : " + e);
        }
    }

    /**
     * 获得object
     * 
     * @param key
     *            key
     * @return object对象
     */
    public Object getObject(String key) {
        try {
            byte[] in = jedis.get(key.getBytes());
            Object obj = SerializationUtil.deserialize(in);

            return obj;
        }
        catch (Exception e) {
            LOGGER.error("getObject key error key: " + key + " error:" + e);
            return null;
        }
    }

    /**
     * 删除指定redis的key
     * 
     * @param key
     *            缓存key
     */
    public void delete(String key) {
        jedis.del(key);
        LOGGER.debug("RedisUtil:delete cache key={}", key);
    }

    /**
     * 删除指定redis的key
     * 
     * @param key
     */
    public void delete(byte[] key) {
        jedis.del(key);
        LOGGER.debug("RedisUtil:delete cache key={}", key);
    }

    /**
     * 返回jedis对象，使用更灵活。 本类中把jedis中好多方法都屏蔽了。
     * 
     * @return 返回JedisCluster
     */
    public JedisCluster getJedis() {
        return this.jedis;
    }

    /**
     * 判断对象是否存在。
     * 
     * @param key
     * @return 返回是否存在
     */
    public boolean exists(String key) {
        boolean res = this.jedis.exists(key);
        LOGGER.debug("RedisUtil:exists key={}, res={}", key, res);
        return res;
    }

    /**
     * 判断对象是否存在。
     * 
     * @param key
     * @return 返回是否存在
     */
    public boolean exists(byte[] key) {
        boolean res = this.jedis.exists(key);
        LOGGER.debug("RedisUtil:exists key={}, res={}", new String(key), res);
        return res;
    }

}