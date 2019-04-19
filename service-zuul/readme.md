端口：8086

利用zuul+ribbon来实现服务网关和负载均衡

1.集成zuul 来做网关和路由转发【其实在转路由的时候感觉已经做法负载均衡了】

2.集成ribbon来实现负载均衡

3.集成admin-client来被admin-server监控

4.对于zuulfilter目前只有全局的拦截，拦截情况分为
pre,pre2,route,post,注意：shouldfilter部分的判断市根据前面是否有
拦截掉，不然后面的拦截器可能会再起作用，导致拦截失败

5.对于指定服务的拦截TODO，【思路：可能根据URL地址】












