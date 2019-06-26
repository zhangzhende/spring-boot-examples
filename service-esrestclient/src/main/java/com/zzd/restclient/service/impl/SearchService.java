

package com.zzd.restclient.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.zzd.restclient.utils.ConstantsES;
import com.zzd.spring.common.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * 查询与业务相关的处理，包括查询检索，索引单个文档
 *
 * @author zhangzhende
 * @version 2018年11月6日
 * @see SearchService
 */

@Service
public class SearchService {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    /**
     *
     */
    @Autowired
    private CommonService commonService;

    /**
     * 索引任意文档，按照传参来控制
     *
     * @param model
     * @return Object
     * @see
     */

    public boolean indexAny(IndexModel model) {
        LOGGER.info("ESService-SearchService-indexAny:索引信息index:{},type:{},START........",
                model.getIndex(), model.getType());
        try {
            commonService.upsertData(model.getIndex(), model.getType(), model.getContentMap());
        } catch (IOException e) {
            LOGGER.error("ESService-SearchService-indexAny:发生错误-未知host", e.getMessage());
            e.printStackTrace();
            return false;
        }
        LOGGER.info("ESService-SearchService-indexAny:索引信息index:{},type:{},........",
                model.getIndex(), model.getType());
        return true;
    }

    /**
     * 更新字段，有则更新，无则插入
     *
     * @param model
     * @return boolean
     * @see
     */
    public boolean updateAny(IndexModel model) {
        LOGGER.info("ESService-SearchService-updateAny:索引信息index:{},type:{},START........",
                model.getIndex(), model.getType());
        try {
            commonService.updateData(model.getIndex(), model.getType(), model.getContentMap());
        } catch (IOException e) {
            LOGGER.error("ESService-SearchService-updateAny:发生错误-未知host", e.getMessage());
            e.printStackTrace();
            return false;
        }
        LOGGER.info("ESService-SearchService-updateAny:索引信息index:{},type:{},........",
                model.getIndex(), model.getType());
        return true;
    }

    /**
     * 查询数据
     *
     * @param param
     * @return Object
     * @see
     */
    @SuppressWarnings("static-access")
//    @HystrixCommand(fallbackMethod = "searchDataError")
    public Object searchData(SearchParam param) {
        PageResult result = new PageResult();
        SearchParam params = checkParam(param);
        JSONObject json = new JSONObject();
        LOGGER.info("ESService-SearchService-searchData:{}", json.toJSONString(param));
        try {
            result = commonService.searchData(params);
        } catch (IOException e) {
            LOGGER.error("ESService-SearchService-searchData:发生错误-未知host", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Object searchDataError(SearchParam param) {
        PageResult result = new PageResult();
        return result;
    }

    /**
     * 通過主鍵查詢
     *
     * @param param
     * @return Object
     * @see
     */
    @SuppressWarnings("static-access")
    public Object searchDataById(IdParam param) {
        Object result = new Object();
        JSONObject json = new JSONObject();
        LOGGER.info("ESService-SearchService-searchDataById:{}", json.toJSONString(param));
        try {
            result = commonService.serachHighById(param);
        } catch (IOException e) {
            LOGGER.error("ESService-SearchService-searchDataById:发生错误-未知host", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * deleteByIds
     *
     * @param model
     * @return boolean
     * @see
     */
    @SuppressWarnings("static-access")
    public boolean deleteByIds(DeleteModel model) {
        JSONObject json = new JSONObject();
        LOGGER.info("ESService-SearchService-deleteByIds:{}", json.toJSONString(model));
        try {
            commonService.deleteDataByIds(model.getIndex(), model.getType(), model.getIds());
        } catch (IOException e) {
            LOGGER.error("ESService-SearchService-deleteByIds:发生错误-未知host", e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 参数验证修改
     *
     * @param param
     * @return SearchParam
     * @see
     */
    public SearchParam checkParam(SearchParam param) {
        if (param.getModelList() != null && param.getModelList().size() > 0) {
            for (SearchModel model : param.getModelList()) {
                // 验证查询类型，防止夏季而传
                if (StringUtils.isNotBlank(model.getSearchType())
                        && (model.getSearchType().equalsIgnoreCase(ConstantsES.SearchType.AND)
                        || model.getSearchType().equalsIgnoreCase(ConstantsES.SearchType.OR)
                        || model.getSearchType().equalsIgnoreCase(ConstantsES.SearchType.NOT)
                        || model.getSearchType().equalsIgnoreCase(ConstantsES.SearchType.NOTEXIST))) {
                } else {
                    model.setSearchType(ConstantsES.SearchType.DEFAULT);
                }
            }
        }
        if (null == param.getPageNum() || param.getPageNum() <= 0) {
            param.setPageNum(1);
        }
        if (null == param.getPageSize() || param.getPageSize() <= 0) {
            param.setPageSize(10);
        }
        if (StringUtils.isBlank(param.getSortType())) {
            param.setSortType(ConstantsES.SortType.DESC);
        }
        return param;
    }

}
