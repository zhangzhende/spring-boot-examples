package com.zzd.spring.api.fallback;

import com.zzd.spring.api.IElasticsearchService;
import com.zzd.spring.common.entity.*;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/4/11 0011.
 */
@Component
public class ElasticearchServiceFallback implements IElasticsearchService{
    @Override
    public AjaxResult<Object> search(SearchParam param) {
        return null;
    }

    @Override
    public AjaxResult<Object> searchById(IdParam param) {
        return null;
    }

    @Override
    public AjaxResult<String> indexData(String sysName, long timestamp, String key, IndexModel model) {
        return null;
    }

    @Override
    public AjaxResult<String> indexAnyData(IndexModel model) {
        return null;
    }

    @Override
    public AjaxResult<String> updateData(String sysName, long timestamp, String key, IndexModel model) {
        return null;
    }

    @Override
    public AjaxResult<String> updateAnyData(IndexModel model) {
        return null;
    }

    @Override
    public AjaxResult<String> deleteData(String sysName, long timestamp, String key, DeleteModel model) {
        return null;
    }

    @Override
    public AjaxResult<String> deleteAnyData(DeleteModel model) {
        return null;
    }
}
