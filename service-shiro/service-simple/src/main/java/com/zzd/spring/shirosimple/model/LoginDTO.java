package com.zzd.spring.shirosimple.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/17 0017.
 */
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 3670470473359711715L;
    @NotBlank(message = "用户名不能为空")
    private String uname;
    @NotBlank(message = "密码不能为空")
    private String pwd;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
