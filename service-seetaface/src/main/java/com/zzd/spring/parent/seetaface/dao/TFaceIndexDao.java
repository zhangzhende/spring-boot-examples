package com.zzd.spring.parent.seetaface.dao;

import com.zzd.spring.parent.seetaface.entity.TFaceIndex;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TFaceIndex)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-03 11:09:56
 */
public interface TFaceIndexDao {

    /**
     * 通过ID查询单条数据
     *
     * @param faceIndex 主键
     * @return 实例对象
     */
    TFaceIndex queryById(Integer faceIndex);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TFaceIndex> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tFaceIndex 实例对象
     * @return 对象列表
     */
    List<TFaceIndex> queryAll(TFaceIndex tFaceIndex);

    /**
     * 新增数据
     *
     * @param tFaceIndex 实例对象
     * @return 影响行数
     */
    int insert(TFaceIndex tFaceIndex);

    /**
     * 修改数据
     *
     * @param tFaceIndex 实例对象
     * @return 影响行数
     */
    int update(TFaceIndex tFaceIndex);

    /**
     * 通过主键删除数据
     *
     * @param faceIndex 主键
     * @return 影响行数
     */
    int deleteById(Integer faceIndex);

    /**
     * 删除所有
     * @return
     */
    int clearAll();
}