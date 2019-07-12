package com.zzd.spring.demoone.dao;

import com.zzd.spring.demoone.entity.TTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TTest)表数据库访问层
 *
 * @author makejava
 * @since 2019-07-11 19:47:18
 */
public interface TTestDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TTest queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TTest> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tTest 实例对象
     * @return 对象列表
     */
    List<TTest> queryAll(TTest tTest);

    /**
     * 新增数据
     *
     * @param tTest 实例对象
     * @return 影响行数
     */
    int insert(TTest tTest);

    /**
     * 修改数据
     *
     * @param tTest 实例对象
     * @return 影响行数
     */
    int update(TTest tTest);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}