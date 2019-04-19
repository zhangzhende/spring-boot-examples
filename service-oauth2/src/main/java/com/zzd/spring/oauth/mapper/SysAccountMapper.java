package com.zzd.spring.oauth.mapper;

import com.zzd.spring.oauth.entity.SysAccount;

/**
 * Created by Administrator on 2019/4/12 0012.
 */

public interface SysAccountMapper {

    /**
     * 根据用户名查询用户
     *
     * @param name
     * @return
     */
    SysAccount loadUserByUsername(String name);
}
