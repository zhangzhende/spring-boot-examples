package com.zzd.spring.shirosimple.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;

/**
 * Created by Administrator on 2019/4/16 0016.
 */
@Configuration
public class ShiroConfig {

    /**
     *
     * @param securityManager
     * @return
     */
//    neanName要写这个，不然会报没有注入
    @Bean(name="shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        filters.put("logout", logoutFilter);
        shiroFilterFactoryBean.setFilters(filters);


        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        filterChainDefinitionMap.put("/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/page/401","anon");
        filterChainDefinitionMap.put("/page/403","anon");
        filterChainDefinitionMap.put("/api/hello","anon");
        filterChainDefinitionMap.put("/api/guest","anon");
        filterChainDefinitionMap.put("/login", "anon");

        filterChainDefinitionMap.put("/**", "authc");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/page/401");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 注入自定义的realm，告诉shiro如何获取用户信息来做登录或者权限控制
     * @return
     */
    @Bean
    public Realm realm(){
        return new CustomRealm();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }

    /**
     *
     * 配置缩写	对应的过滤器	功能
     anon	AnonymousFilter	指定url可以匿名访问
     authc	FormAuthenticationFilter	指定url需要form表单登录，默认会从请求中获取username、password,rememberMe等参数并尝试登录，如果登录不了就会跳转到loginUrl配置的路径。我们也可以用这个过滤器做默认的登录逻辑，但是一般都是我们自己在控制器写登录逻辑的，自己写的话出错返回的信息都可以定制嘛。
     authcBasic	BasicHttpAuthenticationFilter	指定url需要basic登录
     logout	LogoutFilter	登出过滤器，配置指定url就可以实现退出功能，非常方便
     noSessionCreation	NoSessionCreationFilter	禁止创建会话
     perms	PermissionsAuthorizationFilter	需要指定权限才能访问
     port	PortFilter	需要指定端口才能访问
     rest	HttpMethodPermissionFilter	将http请求方法转化成相应的动词来构造一个权限字符串，这个感觉意义不大，有兴趣自己看源码的注释
     roles	RolesAuthorizationFilter	需要指定角色才能访问
     ssl	SslFilter	需要https请求才能访问
     user	UserFilter	需要已登录或“记住我”的用户才能访问
     * 这里统一做鉴权，即判断哪些请求路径需要用户登录，哪些请求路径不需要用户登录。
     * 这里只做鉴权，不做权限控制，因为权限用注解来做。
     * @return
     */
//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
//
//        DefaultShiroFilterChainDefinition chain=new DefaultShiroFilterChainDefinition();
//        //哪些请求可以匿名访问
//        chain.addPathDefinition("/user/login","anon");
//        chain.addPathDefinition("/page/401","anon");
//        chain.addPathDefinition("/page/403","anon");
//        chain.addPathDefinition("/api/hello","anon");
//        chain.addPathDefinition("/api/guest","anon");
//
//        //除了以上的请求外，其它请求都需要登录
//        chain.addPathDefinition("/**", "authc");
//        return chain;
//
//    }

}
