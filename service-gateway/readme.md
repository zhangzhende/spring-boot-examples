端口：9000

1.gateway 在转发多实例服务的时候会自动进行负载均衡，
默认是以轮询的方式

2.本项目对于单项目用了两种过滤方式
    1）.实现GatewayFilter，ordered接口，实现filter方法说明路由拦截的相关操作，包括验证token啊什么的。
        然后可在代码中注入bean说明路由方式
    2）.继承AbstractGatewayFilterFactory，重写apply方法说明路由拦截的相关操作，包括验证token什么的。
        然后再配置文件中可以说明路由方式
       filters:
               - StripPrefix=1
               - Authorize=true
               
       
3.增加了链路追踪zipkin       
       
       
       
       
       
       
       
       
       
       