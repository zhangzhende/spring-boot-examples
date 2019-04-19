package com.zzd.spring.shirosimple.controller;

import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.common.utils.AjaxResultUtils;
import com.zzd.spring.shirosimple.entity.User;
import com.zzd.spring.shirosimple.model.LoginDTO;
import com.zzd.spring.shirosimple.model.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Administrator on 2019/4/17 0017.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login")
    public AjaxResult<UserVo> login(@RequestBody @Validated LoginDTO param) {

        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(new UsernamePasswordToken(param.getUname(), param.getPwd()));
        User user = (User) currentUser.getPrincipal();
        if (null == user) {
            throw new AuthenticationException();
        }
        return AjaxResultUtils.getTrueDataAjaxResult(new UserVo(user));
    }
}
