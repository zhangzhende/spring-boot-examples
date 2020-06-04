package com.zzd.spring.parent.service.blockchain.model;


/**
 * @Description 说明类的用途
 * @ClassName AjaxResultUtil
 * @Author zzd
 * @Create 2019/7/19 10:30
 * @Version 1.0
 **/
public class AjaxResultUtil {
    /**
     * 返回错误 AjaxResult
     *
     * @param <T>
     * @param message
     * @return AjaxResult<?>
     * @author zhangzhende
     * @see
     */
    public static <T> AjaxResult<T> getFalseAjaxResult(AjaxResult<T> ajaxResult, String message, int code) {
        ajaxResult.setCode(code);
        ajaxResult.setMessage(message);
        return ajaxResult;
    }

    /**
     * 返回正确 AjaxResult
     *
     * @param <T>
     * @return AjaxResult<?>
     * @author zhangzhende
     * @see
     */
    public static <T> AjaxResult<T> getTrueAjaxResult(AjaxResult<T> ajaxResult) {
        ajaxResult.setCode(0);
        ajaxResult.setMessage("OK");
        return ajaxResult;
    }

}
