package com.zzd.spring.parent.seetaface.service;

import com.zzd.spring.parent.seetaface.entity.TFaceIndex;
import java.util.List;

/**
 * (TFaceIndex)表服务接口
 *
 * @author makejava
 * @since 2019-12-03 11:09:56
 */
public interface TFaceIndexService {

    /**
     * 通过ID查询单条数据
     *
     * @param faceIndex 主键
     * @return 实例对象
     */
    TFaceIndex queryById(Integer faceIndex);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TFaceIndex> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tFaceIndex 实例对象
     * @return 实例对象
     */
    TFaceIndex insert(TFaceIndex tFaceIndex);

    /**
     * 修改数据
     *
     * @param tFaceIndex 实例对象
     * @return 实例对象
     */
    TFaceIndex update(TFaceIndex tFaceIndex);

    /**
     * 通过主键删除数据
     *
     * @param faceIndex 主键
     * @return 是否成功
     */
    boolean deleteById(Integer faceIndex);

}