package com.zzd.spring.normal.controller;

import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.normal.enums.ResultStatusCode;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/17 0017.
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController {
    /**
     * 未授权跳转方法
     *
     * @return
     */
    @RequestMapping("/unauth")
    public AjaxResult<String> unauth() {
        SecurityUtils.getSubject().logout();
        return new AjaxResult<String>(ResultStatusCode.UNAUTHO_ERROR.getCode(), ResultStatusCode.UNAUTHO_ERROR.getMsg());
    }

    /**
     * 被踢出后跳转方法
     *
     * @return
     */
    @RequestMapping("/kickout")
    public AjaxResult<String> kickout() {
        return new AjaxResult<String>(ResultStatusCode.INVALID_TOKEN.getCode(), ResultStatusCode.INVALID_TOKEN.getMsg());
    }
}
