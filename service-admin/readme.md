端口：8084


tip1：
java.lang.IllegalStateException: Calling [asyncError()] 
is not valid for a request with Async state

这个摒弃了tomcat作为web容器，换为jetty，这点在pom文件中可以看到，
在tomcat中会报上述异常，但是可能没有影响，也可以继续使用tomcat

tip2:
项目集成spring-security增加了登录页面，
放开所有actuator,这样在监控页面会看到详细信息
配置文件里面默认用户名密码

1.spring admin 服务端，监控admin-client的服务状态，可在页面上看到服务的运行情况

2.client放开什么接口，然后就能在上面看到什么的养的接口
management:
  endpoints:
    web:
      exposure:
        include: "*"//这里是将client端所有接口都暴露，注意在实际使用的时候选择性开发监控
        
3.集成spring security，拦截部分URL，放开部分URL        
        
        
        
        
        
        
        
        
        
        
        