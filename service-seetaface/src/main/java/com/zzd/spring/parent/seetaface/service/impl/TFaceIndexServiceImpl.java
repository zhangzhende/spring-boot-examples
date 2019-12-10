package com.zzd.spring.parent.seetaface.service.impl;

import com.zzd.spring.parent.seetaface.entity.TFaceIndex;
import com.zzd.spring.parent.seetaface.dao.TFaceIndexDao;
import com.zzd.spring.parent.seetaface.service.TFaceIndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TFaceIndex)表服务实现类
 *
 * @author makejava
 * @since 2019-12-03 11:09:56
 */
@Service("tFaceIndexService")
public class TFaceIndexServiceImpl implements TFaceIndexService {
    @Resource
    private TFaceIndexDao tFaceIndexDao;

    /**
     * 通过ID查询单条数据
     *
     * @param faceIndex 主键
     * @return 实例对象
     */
    @Override
    public TFaceIndex queryById(Integer faceIndex) {
        return this.tFaceIndexDao.queryById(faceIndex);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TFaceIndex> queryAllByLimit(int offset, int limit) {
        return this.tFaceIndexDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tFaceIndex 实例对象
     * @return 实例对象
     */
    @Override
    public TFaceIndex insert(TFaceIndex tFaceIndex) {
        this.tFaceIndexDao.insert(tFaceIndex);
        return tFaceIndex;
    }

    /**
     * 修改数据
     *
     * @param tFaceIndex 实例对象
     * @return 实例对象
     */
    @Override
    public TFaceIndex update(TFaceIndex tFaceIndex) {
        this.tFaceIndexDao.update(tFaceIndex);
        return this.queryById(tFaceIndex.getFaceIndex());
    }

    /**
     * 通过主键删除数据
     *
     * @param faceIndex 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer faceIndex) {
        return this.tFaceIndexDao.deleteById(faceIndex) > 0;
    }
}