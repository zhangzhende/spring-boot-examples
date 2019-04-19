package com.zzd.spring.api.fallback;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zzd.spring.api.IMongoservice;
import com.zzd.spring.common.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2019/4/10 0010.
 */
@Component
public class MongoServiceFallback implements IMongoservice {
    @Override
    public void save4Primary(User user) {

    }

    @Override
    public void save4Secondary(User user) {

    }

    @Override
    public List<Object> findPrimaryAll() {
        return null;
    }

    @Override
    public List<Object> findSecondaryAll() {
        return null;
    }

    @Override
    public UpdateResult updatePrimaryOne(User user) {
        return null;
    }

    @Override
    public UpdateResult updatePrimaryAll(User user) {
        return null;
    }

    @Override
    public DeleteResult deletePrimary(User user) {
        return null;
    }

    @Override
    public UpdateResult updateSecondaryAll(User user) {
        return null;
    }
}
