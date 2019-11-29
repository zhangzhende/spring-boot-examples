package com.zzd.service.arcsoft.service;

import com.zzd.service.arcsoft.entity.TUserFace;
import java.util.List;

/**
 * (TUserFace)表服务接口
 *
 * @author makejava
 * @since 2019-11-28 10:22:34
 */
public interface TUserFaceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TUserFace queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TUserFace> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tUserFace 实例对象
     * @return 实例对象
     */
    TUserFace insert(TUserFace tUserFace);

    /**
     * 修改数据
     *
     * @param tUserFace 实例对象
     * @return 实例对象
     */
    TUserFace update(TUserFace tUserFace);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}