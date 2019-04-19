package com.zzd.spring.api;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zzd.spring.api.config.FeignConfig;
import com.zzd.spring.api.fallback.MongoServiceFallback;
import com.zzd.spring.common.entity.User;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * Created by Administrator on 2019/4/10 0010.
 */
@FeignClient(value = "service-mongo", configuration = FeignConfig.class,fallback = MongoServiceFallback.class)
public interface IMongoservice {
    public void save4Primary(User user);

    /**
     * 为库2插入用户
     *
     * @param user
     */
    public void save4Secondary(User user);

    /**
     * 查询库1所有记录
     *
     * @return
     */
    public List<Object> findPrimaryAll();

    /**
     * 查询库2所有记录
     *
     * @return
     */
    public List<Object> findSecondaryAll();

    /**
     * 更新库1文档，更新符合查询条件的第一个
     *
     * @param user
     */
    public UpdateResult updatePrimaryOne(User user);

    /**
     * 更新库1符合条件的所有
     *
     * @param user
     */
    public UpdateResult updatePrimaryAll(User user);

    /**
     * 删除库1中符合查条件的文档
     *
     * @param user
     * @return
     */
    public DeleteResult deletePrimary(User user);


    /**
     * 更新库2符合条件的所有
     *
     * @param user
     */
    public UpdateResult updateSecondaryAll(User user);
}
