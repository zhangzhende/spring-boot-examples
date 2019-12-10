package com.zzd.spring.parent.seetaface.service.impl;

import com.zzd.spring.parent.seetaface.entity.TUserFaces;
import com.zzd.spring.parent.seetaface.dao.TUserFacesDao;
import com.zzd.spring.parent.seetaface.service.TUserFacesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TUserFaces)表服务实现类
 *
 * @author makejava
 * @since 2019-12-03 10:26:13
 */
@Service("tUserFacesService")
public class TUserFacesServiceImpl implements TUserFacesService {
    @Resource
    private TUserFacesDao tUserFacesDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TUserFaces queryById(Integer id) {
        return this.tUserFacesDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TUserFaces> queryAllByLimit(int offset, int limit) {
        return this.tUserFacesDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tUserFaces 实例对象
     * @return 实例对象
     */
    @Override
    public TUserFaces insert(TUserFaces tUserFaces) {
        this.tUserFacesDao.insert(tUserFaces);
        return tUserFaces;
    }

    /**
     * 修改数据
     *
     * @param tUserFaces 实例对象
     * @return 实例对象
     */
    @Override
    public TUserFaces update(TUserFaces tUserFaces) {
        this.tUserFacesDao.update(tUserFaces);
        return this.queryById(tUserFaces.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tUserFacesDao.deleteById(id) > 0;
    }
}