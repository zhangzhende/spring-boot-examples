package com.zzd.spring.shirosimple.controller;

import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.common.utils.AjaxResultUtils;
import org.apache.shiro.authz.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

@RestController
@RequestMapping(value = "/api")
public class AuthController {


    /**
     * 由于ShiroConfig中配置了该路径可以匿名访问，所以这接口不需要登录就能访问
     * @return
     */
    @RequestMapping(value = "/hello")
    public AjaxResult<String> hello() {
        return AjaxResultUtils.getTrueDataAjaxResult("hello ");
    }

    /**
     * 如果ShiroConfig中没有配置该路径可以匿名访问，所以直接被登录过滤了。
     * 如果配置了可以匿名访问，那这里在没有登录的时候可以访问，但是用户登录后就不能访问
     * @return
     */
    @RequiresGuest
    @RequestMapping(value = "/guest")
    public AjaxResult<String> guest(){
        return AjaxResultUtils.getTrueDataAjaxResult("@RequiresGuest");
    }

    /**
     *
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/authn")
    public AjaxResult<String> authn(){
        return AjaxResultUtils.getTrueDataAjaxResult("@RequiresAuthentication");
    }

    @RequiresUser
    @RequestMapping(value = "/user")
    public AjaxResult<String> user(){
        return AjaxResultUtils.getTrueDataAjaxResult("@RequiresUser");
    }

    @RequiresPermissions(value = "install")
    @RequestMapping(value = "/install")
    public AjaxResult<String> install(){
        return AjaxResultUtils.getTrueDataAjaxResult("permissions install");
    }

    @RequiresPermissions(value = "build")
    @RequestMapping(value = "/build")
    public AjaxResult<String> build(){
        return AjaxResultUtils.getTrueDataAjaxResult("permissions build");
    }

    @RequiresRoles(value = "js")
    @RequestMapping(value = "/rolejs")
    public AjaxResult<String> rolejs(){
        return AjaxResultUtils.getTrueDataAjaxResult(" role  js");
    }
    @RequiresRoles(value = "python")
    @RequestMapping(value = "/rolepython")
    public AjaxResult<String> rolepython(){
        return AjaxResultUtils.getTrueDataAjaxResult(" role  python");
    }
}






























