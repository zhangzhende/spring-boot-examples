package com.zzd.service.arcsoft.dao;

import com.zzd.service.arcsoft.entity.TUserFace;
import com.zzd.service.arcsoft.model.dto.FaceUserInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TUserFace)表数据库访问层
 *
 * @author makejava
 * @since 2019-11-28 10:22:33
 */
public interface TUserFaceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TUserFace queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TUserFace> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tUserFace 实例对象
     * @return 对象列表
     */
    List<TUserFace> queryAll(TUserFace tUserFace);

    /**
     * 新增数据
     *
     * @param tUserFace 实例对象
     * @return 影响行数
     */
    int insert(TUserFace tUserFace);

    /**
     * 修改数据
     *
     * @param tUserFace 实例对象
     * @return 影响行数
     */
    int update(TUserFace tUserFace);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 通过groupId查询face信息
     * @param groupId
     * @return
     */
    List<FaceUserInfo> getUserFaceInfoByGroupId(Integer groupId);
}