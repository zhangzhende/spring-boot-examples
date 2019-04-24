package com.zzd.spring.mongodb.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zzd.spring.common.entity.User;
import com.zzd.spring.mongodb.config.PrimaryMongoConfig;
import com.zzd.spring.mongodb.config.SecondaryMongoConfig;
import com.zzd.spring.mongodb.repository.primary.PrimaryRepository;
import com.zzd.spring.mongodb.repository.secondary.SecondaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务
 * Created by Administrator on 2019/4/1 0001.
 */
@Service
public class MongoService {

    /**
     * 第一数据源
     */
    @Autowired
    private PrimaryRepository primaryRepository;
    /**
     * 第二数据源
     */
    @Autowired
    private SecondaryRepository secondaryRepository;
    /**
     * 第一数据源template
     */
    @Autowired
    @Qualifier(value = PrimaryMongoConfig.MONGO_TEMPLATE)
    private MongoTemplate primaryMongoTemplate;
    /**
     * 第二数据源template
     */
    @Autowired
    @Qualifier(value = SecondaryMongoConfig.MONGO_TEMPLATE)
    private MongoTemplate secondaryMongoTemplate;

    /**
     * 为库1插入用户
     *
     * @param user
     */
    public void save4Primary(User user) {
        primaryRepository.save(user);

    }

    /**
     * 为库2插入用户
     *
     * @param user
     */
    public void save4Secondary(User user) {
        secondaryRepository.save(user);
    }

    /**
     * 查询库1所有记录
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "findPrimaryAllError")
    public List<Object> findPrimaryAll() {
        List<User> userList = primaryRepository.findAll();

        return new ArrayList<>(userList);
    }
    public List<Object> findPrimaryAllError() {
        return new ArrayList<>();
    }
    /**
     * 查询库2所有记录
     *
     * @return
     */
    public List<Object> findSecondaryAll() {
        List<User> userList = secondaryRepository.findAll();
        return new ArrayList<>(userList);
    }

    /**
     * 更新库1文档，更新符合查询条件的第一个
     *
     * @param user
     */
    public UpdateResult updatePrimaryOne(User user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = buildUpdate(user);
//        更新第一个
        UpdateResult result = primaryMongoTemplate.updateFirst(query, update, User.class);
        return result;
    }

    /**
     * 更新库1符合条件的所有
     *
     * @param user
     */
    public UpdateResult updatePrimaryAll(User user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = buildUpdate(user);

//        更新所有
        UpdateResult result = primaryMongoTemplate.updateMulti(query, update, User.class);
        return result;
    }

    /**
     * 删除库1中符合查条件的文档
     *
     * @param user
     * @return
     */
    public DeleteResult deletePrimary(User user) {
        Query query = buildQuery(user);
        DeleteResult result = primaryMongoTemplate.remove(query, User.class,"user");
        return result;
    }


    /**
     * 更新库2符合条件的所有
     *
     * @param user
     */
    public UpdateResult updateSecondaryAll(User user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = buildUpdate(user);
//        更新所有
        UpdateResult result = secondaryMongoTemplate.updateMulti(query, update, User.class);
        return result;
    }

    /**
     * 构建查询条件
     *
     * @param user
     * @return
     */
    public Query buildQuery(User user) {
        Query query = new Query();
        Map<String, Object> map = parseToMap(user);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
        }
        return query;
    }

    /**
     * 实体转MAP
     *
     * @param object
     * @return
     */
    public Map<String, Object> parseToMap(Object object) {
        //即使是null也要保留，否贼会被舍弃，SerializerFeature.WriteMapNullValue
//        Map<String, Object> map = JSONObject.parseObject(
//                JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue), Map.class);
//        不要 null值
        Map<String, Object> map = JSONObject.parseObject(
                JSONObject.toJSONString(object), Map.class);
        return map;
    }

    /**
     * 更新条件构建
     *
     * @param user
     * @return
     */
    public Update buildUpdate(User user) {
        Update update = new Update();
        Map<String, Object> map = parseToMap(user);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        return update;
    }

}
