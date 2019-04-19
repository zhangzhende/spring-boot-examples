package com.zzd.spring.normal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.normal.config.LoginType;
import com.zzd.spring.normal.config.UserToken;
import com.zzd.spring.normal.enums.ResultStatusCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/17 0017.
 */
@RestController
@RequestMapping("/admin")
public class LoginController {

    /**
     * 用户密码登录
     *
     * @param loginName
     * @param pwd
     * @return
     */
    @RequestMapping("/login")
    public AjaxResult login(String loginName, String pwd) {
        UserToken token = new UserToken(LoginType.USER_PASSWORD, loginName, pwd);
        return shiroLogin(token);
    }

    /**
     * 手机验证码登录
     * 注：由于是demo演示，此处不添加发送验证码方法；
     * 正常操作：发送验证码至手机并且将验证码存放在redis中，登录的时候比较用户穿过来的验证码和redis中存放的验证码
     *
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping("phoneLogin")
    public AjaxResult phoneLogin(String phone, String code) {
        // 此处phone替换了username，code替换了password
        UserToken token = new UserToken(LoginType.USER_PHONE, phone, code);
        return shiroLogin(token);
    }

    /**
     * 微信登录
     * 注：由于是demo演示，此处只接收一个code参数（微信会生成一个code，然后通过code获取openid等信息）
     * 其他根据个人实际情况添加参数
     *
     * @param code
     * @return
     */
    @RequestMapping("wechatLogin")
    public AjaxResult wechatLogin(String code) {
        // 此处假装code分别是username、password
        UserToken token = new UserToken(LoginType.WECHAT_LOGIN, code, code, code);
        return shiroLogin(token);
    }

    public AjaxResult shiroLogin(UserToken token) {
        try {
            //登录不在该处处理，交由shiro处理
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            if (subject.isAuthenticated()) {
                JSON json = new JSONObject();
                ((JSONObject) json).put("token", subject.getSession().getId());

                return new AjaxResult(true, ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), json);
            } else {
                return new AjaxResult<String>(false, ResultStatusCode.SHIRO_ERROR.getCode(), ResultStatusCode.SHIRO_ERROR.getMsg(), "");
            }
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            e.printStackTrace();
            return new AjaxResult<String>(false, ResultStatusCode.NOT_EXIST_USER_OR_ERROR_PWD.getCode(),
                    ResultStatusCode.NOT_EXIST_USER_OR_ERROR_PWD.getMsg(), "");
        } catch (LockedAccountException e) {
            return new AjaxResult<String>(false, ResultStatusCode.USER_FROZEN.getCode(),
                    ResultStatusCode.USER_FROZEN.getMsg(), "");
        } catch (Exception e) {
            return new AjaxResult<String>(false, ResultStatusCode.SYSTEM_ERR.getCode(),
                    ResultStatusCode.SYSTEM_ERR.getMsg(), "");
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping("/logout")
    public AjaxResult logout() {
        SecurityUtils.getSubject().logout();
        return new AjaxResult(true, ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), "");
    }


}
