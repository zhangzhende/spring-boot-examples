package com.zzd.spring.normal.controller;

import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.common.utils.AjaxResultUtils;
import com.zzd.spring.normal.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/17 0017.
 */
@RestController
@RequestMapping(value = "/admin/user")
public class UserController {

    @Autowired
    private UserService userService;
    @RequiresPermissions("install")
    @RequestMapping("/findList")
    public AjaxResult<String> findList(){
        return AjaxResultUtils.getTrueDataAjaxResult("OK");
    }
}
