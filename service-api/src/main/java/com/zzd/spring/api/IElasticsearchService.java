package com.zzd.spring.api;

import com.zzd.spring.api.config.FeignConfig;
import com.zzd.spring.api.fallback.ElasticearchServiceFallback;
import com.zzd.spring.common.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2019/4/11 0011.
 */
@FeignClient(value = "service-es", configuration = FeignConfig.class, fallback = ElasticearchServiceFallback.class)
@RequestMapping(value = "/search/api")
public interface IElasticsearchService {
    /**
     * /**
     * 查询数据
     *
     * @return Object
     * @see
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public AjaxResult<Object> search(@RequestBody @Validated SearchParam param);

    /**
     * 根据id查询
     *
     * @param param
     * @return AjaxResult
     * @see
     */

    @RequestMapping(value = "/searchById", method = RequestMethod.POST)
    public AjaxResult<Object> searchById(@RequestBody @Validated IdParam param);

    /**
     * 索引任何文档,通用接口
     *
     * @param model
     * @return Object
     * @see
     */

    @RequestMapping(value = "/indexData", method = RequestMethod.POST)
    public AjaxResult<String> indexData(@RequestHeader("sysName") String sysName,
                                        @RequestHeader("timestamp") long timestamp,
                                        @RequestHeader("key") String key,
                                        @RequestBody IndexModel model);

    /**
     * 索引任何文档,通用接口
     *
     * @param model
     * @return Object
     * @see
     */

    @RequestMapping(value = "/indexAnyData", method = RequestMethod.POST)
    public AjaxResult<String> indexAnyData(@RequestBody IndexModel model);

    /**
     * 更新字段
     *
     * @param model
     * @return
     * @see
     */

    @RequestMapping(value = "/updateData", method = RequestMethod.POST)
    public AjaxResult<String> updateData(@RequestHeader("sysName") String sysName,
                                         @RequestHeader("timestamp") long timestamp,
                                         @RequestHeader("key") String key,
                                         @RequestBody IndexModel model);


    @RequestMapping(value = "/updateAnyData", method = RequestMethod.POST)
    public AjaxResult<String> updateAnyData(@RequestBody IndexModel model);

    /**
     * 删除文档,通用接口
     *
     * @param sysName
     * @param timestamp
     * @param key
     * @param model
     * @return Object
     * @see
     */

    @RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public AjaxResult<String> deleteData(@RequestHeader("sysName") String sysName,
                                         @RequestHeader("timestamp") long timestamp,
                                         @RequestHeader("key") String key,
                                         @RequestBody DeleteModel model);

    /**
     * deleteAnyData
     *
     * @param model
     * @return AjaxResult
     * @see
     */

    @RequestMapping(value = "/deleteAnyData", method = RequestMethod.POST)
    public AjaxResult<String> deleteAnyData(@RequestBody @Validated DeleteModel model);
}
