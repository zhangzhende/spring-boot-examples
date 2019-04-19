package com.zzd.spring.shirosimple.controller;

import com.zzd.spring.common.entity.AjaxResult;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/17 0017.
 */
@RestController
@RequestMapping(value = "/page")
public class PageController {

    /**
     * // shiro.loginUrl映射到这里，这里直接抛出异常交给GlobalExceptionHandler来统一返回json信息，
     *
     * @return
     */
    @RequestMapping(value = "/401")
    public AjaxResult<String> page401() {
        throw new UnauthenticatedException();
    }

    /**
     * shiro.unauthorizedUrl映射到这里。由于统一约定了url方式只做鉴权控制，不做权限访问控制，
     * 也就是说在ShiroConfig中如果没有roles[js],perms[mvn:install]这样的权限访问控制配置的话，
     * 是不会跳转到这里的。
     *
     * @return
     */
    @RequestMapping(value = "/403")
    public AjaxResult<String> page403() {
        throw new UnauthorizedException();
    }

    @RequestMapping(value = "/index")
    public AjaxResult<String> pageIndex() {
        return new AjaxResult<>(true, "ok", "200", "index data");
    }
}
