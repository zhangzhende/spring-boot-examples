package com.zzd.spring.common.utils;

import com.zzd.spring.common.entity.AjaxResult;

/**
 * Created by Administrator on 2019/4/8 0008.
 */
public class AjaxResultUtils {

    /**
     * 返回错误 AjaxResult
     * @param ajaxResult
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> getFalseAjaxResult(AjaxResult<T> ajaxResult, String message) {
        ajaxResult.setCode("500");
        ajaxResult.setMessage(message);
        ajaxResult.setSuccess(false);
        return ajaxResult;
    }

    /**
     * 返回正确 AjaxResult
     * @param ajaxResult
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> getTrueAjaxResult(AjaxResult<T> ajaxResult) {
        ajaxResult.setCode("200");
        ajaxResult.setMessage("ok");
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 带data获取完整result
     * @param data
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> getTrueDataAjaxResult(T data) {
        AjaxResult<T> ajaxResult =new AjaxResult<>();
        ajaxResult.setCode("200");
        ajaxResult.setMessage("ok");
        ajaxResult.setSuccess(true);
        ajaxResult.setData(data);
        return ajaxResult;
    }
}
