package com.zzd.spring.normal.mapper;

import com.zzd.spring.normal.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * Created by Administrator on 2019/4/16 0016.
 */
@Mapper
public interface UserMapper {

    /**
     *通过用户标识获取用户权限
     * @param userId
     * @return
     */
    Set<String> getPermsByUserId(Long userId);

    /**
     * 通过用户标识获取用户角色
     * @param userId
     * @return
     */
    Set<String> getRolesByUserId(Long userId);

    /**
     * 通过用户名查询用户，这里要求用户名唯一
     * @param user
     * @return
     */
     User findUserByName(String user);
}
