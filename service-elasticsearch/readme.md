端口：8081

1.elasticsearch相关操作的简单demo
这里只包含增删改查

更加细致的在另外的项目，包括聚合等

补充：
a demo ,使用java编写的与es交互的例子，里面使用了常用的操作方法，基本上能满足哦大多数查询需求，
简单查询，索引管理，文档管理，聚合查询分析等【对于插叙组装等地方使用的xml或者json的builder,
感觉可以考虑为object或者map然后转为jsonstring】

2.增加了断路由

3.增加了spring-admin-client可在spring-admin-server上监控服务运行状态

4.增加zipkin 链路跟踪相关，但是链路跟踪服务用人家一个完整的jar包，
可利用配置再修改

5.增加feignclient远程调用【尚未测试可用情况】











