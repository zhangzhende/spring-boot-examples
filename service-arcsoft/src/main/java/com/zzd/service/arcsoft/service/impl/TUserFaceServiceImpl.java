package com.zzd.service.arcsoft.service.impl;

import com.zzd.service.arcsoft.entity.TUserFace;
import com.zzd.service.arcsoft.dao.TUserFaceDao;
import com.zzd.service.arcsoft.service.TUserFaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TUserFace)表服务实现类
 *
 * @author makejava
 * @since 2019-11-28 10:22:34
 */
@Service("tUserFaceService")
public class TUserFaceServiceImpl implements TUserFaceService {
    @Resource
    private TUserFaceDao tUserFaceDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TUserFace queryById(Integer id) {
        return this.tUserFaceDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TUserFace> queryAllByLimit(int offset, int limit) {
        return this.tUserFaceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tUserFace 实例对象
     * @return 实例对象
     */
    @Override
    public TUserFace insert(TUserFace tUserFace) {
        this.tUserFaceDao.insert(tUserFace);
        return tUserFace;
    }

    /**
     * 修改数据
     *
     * @param tUserFace 实例对象
     * @return 实例对象
     */
    @Override
    public TUserFace update(TUserFace tUserFace) {
        this.tUserFaceDao.update(tUserFace);
        return this.queryById(tUserFace.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tUserFaceDao.deleteById(id) > 0;
    }
}