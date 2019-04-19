package com.zzd.spring.mongodb.repository.primary;

import com.zzd.spring.common.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2019/4/1 0001.
 */
public interface PrimaryRepository extends MongoRepository<User,String>{
}
