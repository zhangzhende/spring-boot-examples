package com.zzd.restclient.controller;


import com.zzd.restclient.config.ValueConfig;
import com.zzd.restclient.service.impl.SearchService;
import com.zzd.restclient.utils.ConstantsES;
import com.zzd.restclient.utils.MD5Utils;
import com.zzd.spring.common.entity.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * ES服务
 *
 * @author zhangzhende
 * @version 2018年11月5日
 * @see Controller
 * @since
 */
@RestController
@Api(value = "搜索引擎-数据查询与数据索引", protocols = "http/https", tags = "搜索引擎-数据查询与数据索引")
@RequestMapping(value = "/search/api")
public class Controller {
    /**
     *
     */
    @Autowired
    private SearchService service;

    /**
     *
     */
    @Autowired
    private ValueConfig valueConfig;

    /**
     * 查询数据
     *
     * @return Object
     * @see
     */
    @ApiOperation(value = "查询任意文档，通用接口", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({})
    @ApiResponses({})
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public AjaxResult<Object> search(@RequestBody @Validated SearchParam param) {
        AjaxResult<Object> ajaxResult = new AjaxResult<Object>();
        try {
            Object obj = service.searchData(param);
            ajaxResult = getTrueAjaxResult(ajaxResult);
            ajaxResult.setData(obj);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
        }
        return ajaxResult;
    }

    /**
     * 根据id查询
     *
     * @param param
     * @return AjaxResult
     * @see
     */
    @ApiOperation(value = "通過主鍵編號查詢文檔", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({})
    @ApiResponses({})
    @RequestMapping(value = "/searchById", method = RequestMethod.POST)
    public AjaxResult<Object> searchById(@RequestBody @Validated IdParam param) {
        AjaxResult<Object> ajaxResult = new AjaxResult<Object>();
        try {
            Object obj = service.searchDataById(param);
            ajaxResult = getTrueAjaxResult(ajaxResult);
            ajaxResult.setData(obj);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
        }
        return ajaxResult;
    }

    /**
     * 索引任何文档,通用接口
     *
     * @param model
     * @return Object
     * @see
     */
    @ApiOperation(value = "索引任何文档,通用接口", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysName", value = "系统名称,放在header中传递", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "timestamp", value = "访问时间,放在header中传递", required = true, dataType = "long", paramType = "header"),
            @ApiImplicitParam(name = "key", value = "秘钥,放在header中传递", required = true, dataType = "String", paramType = "header")})
    @ApiResponses({})
    @RequestMapping(value = "/indexData", method = RequestMethod.POST)
    public AjaxResult<String> indexData(@RequestHeader("sysName") String sysName,
                                        @RequestHeader("timestamp") long timestamp,
                                        @RequestHeader("key") String key,
                                        @RequestBody IndexModel model) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (model.getContentMap().size() < ConstantsES.BATCH) {
            if (MD5Utils.check(sysName, timestamp, key, valueConfig.getTimeDifference())) {
                if (service.indexAny(model)) {
                    ajaxResult = getTrueAjaxResult(ajaxResult);
                } else {
                    ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
                }
            } else {
                ajaxResult = getFalseAjaxResult(ajaxResult, "you have no right");
            }
        } else {
            ajaxResult = getFalseAjaxResult(ajaxResult,
                    "content too large ,no more than " + ConstantsES.BATCH);
        }
        return ajaxResult;
    }

    /**
     * 索引任何文档,通用接口
     *
     * @param model
     * @return Object
     * @see
     */
    @ApiOperation(value = "索引任何文档,通用接口，有则覆盖，无则插入，试用，未加密验证", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({})
    @ApiResponses({})
    @RequestMapping(value = "/indexAnyData", method = RequestMethod.POST)
    public AjaxResult<String> indexAnyData(@RequestBody IndexModel model) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (model.getContentMap().size() < ConstantsES.BATCH) {
            if (service.indexAny(model)) {
                ajaxResult = getTrueAjaxResult(ajaxResult);
            } else {
                ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
            }
        } else {
            ajaxResult = getFalseAjaxResult(ajaxResult,
                    "content too large ,no more than " + ConstantsES.BATCH);

        }
        return ajaxResult;
    }

    /**
     * 更新字段
     *
     * @param model
     * @return
     * @see
     */
    @ApiOperation(value = "更新字段，任何文档,通用接口，有则更新，无则插入", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysName", value = "系统名称,放在header中传递", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "timestamp", value = "访问时间,放在header中传递", required = true, dataType = "long", paramType = "header"),
            @ApiImplicitParam(name = "key", value = "秘钥,放在header中传递", required = true, dataType = "String", paramType = "header")})
    @ApiResponses({})
    @RequestMapping(value = "/updateData", method = RequestMethod.POST)
    public AjaxResult<String> updateData(@RequestHeader("sysName") String sysName,
                                         @RequestHeader("timestamp") long timestamp,
                                         @RequestHeader("key") String key,
                                         @RequestBody IndexModel model) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        //检查量
        if (model.getContentMap().size() < ConstantsES.BATCH) {
            //检查权限
            if (MD5Utils.check(sysName, timestamp, key, valueConfig.getTimeDifference())) {
                if (service.updateAny(model)) {
                    ajaxResult = getTrueAjaxResult(ajaxResult);
                } else {
                    ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
                }
            } else {
                ajaxResult = getFalseAjaxResult(ajaxResult, "you have no right");
            }
        } else {
            ajaxResult = getFalseAjaxResult(ajaxResult,
                    "content too large ,no more than " + ConstantsES.BATCH);
        }
        return ajaxResult;
    }

    @ApiOperation(value = "更新字段，任何文档,通用接口，有则更新，无则插入，试用，未加密验证", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({})
    @ApiResponses({})
    @RequestMapping(value = "/updateAnyData", method = RequestMethod.POST)
    public AjaxResult<String> updateAnyData(@RequestBody IndexModel model) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (model.getContentMap().size() < ConstantsES.BATCH) {
            if (service.updateAny(model)) {
                ajaxResult = getTrueAjaxResult(ajaxResult);
            } else {
                ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
            }
        } else {
            ajaxResult = getFalseAjaxResult(ajaxResult,
                    "content too large ,no more than " + ConstantsES.BATCH);
        }
        return ajaxResult;
    }

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
    @ApiOperation(value = "删除文档,通用接口", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysName", value = "系统名称,放在header中传递", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "timestamp", value = "访问时间,放在header中传递", required = true, dataType = "long", paramType = "header"),
            @ApiImplicitParam(name = "key", value = "秘钥,放在header中传递", required = true, dataType = "String", paramType = "header")})
    @ApiResponses({})
    @RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public AjaxResult<String> deleteData(@RequestHeader("sysName") String sysName,
                                         @RequestHeader("timestamp") long timestamp,
                                         @RequestHeader("key") String key,
                                         @RequestBody DeleteModel model) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (model.getIds().size() < ConstantsES.BATCH) {
            if (MD5Utils.check(sysName, timestamp, key, valueConfig.getTimeDifference())) {
                if (service.deleteByIds(model)) {
                    ajaxResult = getTrueAjaxResult(ajaxResult);
                } else {
                    ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
                }
            } else {
                ajaxResult = getFalseAjaxResult(ajaxResult, "you have no right");
            }
        } else {
            ajaxResult = getFalseAjaxResult(ajaxResult,
                    "content too large ,no more than " + ConstantsES.BATCH);
        }
        return ajaxResult;
    }

    /**
     * deleteAnyData
     *
     * @param model
     * @return AjaxResult
     * @see
     */
    @ApiOperation(value = "删除文档,通用接口,无权限验证", notes = "可以通过传入不同的参数来实现【张振德】")
    @ApiImplicitParams({})
    @ApiResponses({})
    @RequestMapping(value = "/deleteAnyData", method = RequestMethod.POST)
    public AjaxResult<String> deleteAnyData(@RequestBody @Validated DeleteModel model) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (model.getIds().size() < ConstantsES.BATCH) {
            if (service.deleteByIds(model)) {
                ajaxResult = getTrueAjaxResult(ajaxResult);
            } else {
                ajaxResult = getFalseAjaxResult(ajaxResult, "something wrong");
            }
        } else {
            ajaxResult = getFalseAjaxResult(ajaxResult,
                    "content too large ,no more than " + ConstantsES.BATCH);
        }
        return ajaxResult;
    }

    /**
     * 返回错误 AjaxResult
     *
     * @param <T>
     * @param message
     * @return AjaxResult<?>
     * @see
     */
    public <T> AjaxResult<T> getFalseAjaxResult(AjaxResult<T> ajaxResult, String message) {
        ajaxResult.setCode(ConstantsES.Error.ERR_CODE_100);
        ajaxResult.setMessage(message);
        ajaxResult.setSuccess(false);
        return ajaxResult;
    }

    /**
     * 返回正确 AjaxResult
     *
     * @param <T>
     * @return AjaxResult<?>
     * @see
     */
    public <T> AjaxResult<T> getTrueAjaxResult(AjaxResult<T> ajaxResult) {
        ajaxResult.setCode(ConstantsES.Success.DATA_SUCC);
        ajaxResult.setMessage("OK");
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
