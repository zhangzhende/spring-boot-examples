package com.zzd.spring.parent.seetaface.dao;

import com.zzd.spring.parent.seetaface.entity.TUserFaces;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TUserFaces)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-03 10:26:12
 */
public interface TUserFacesDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TUserFaces queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TUserFaces> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tUserFaces 实例对象
     * @return 对象列表
     */
    List<TUserFaces> queryAll(TUserFaces tUserFaces);

    /**
     * 新增数据
     *
     * @param tUserFaces 实例对象
     * @return 影响行数
     */
    int insert(TUserFaces tUserFaces);

    /**
     * 修改数据
     *
     * @param tUserFaces 实例对象
     * @return 影响行数
     */
    int update(TUserFaces tUserFaces);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    String findKeyByIndex(Integer index);

    void deleteFaceImgs(List<String> list);
}