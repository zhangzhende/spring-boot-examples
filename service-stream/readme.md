端口：8090，8091，8092

Spring cloud Stream 项目相关
1.集成spring admin client  监控系统情况

2.集成eureka 服务注册与发现

3.集成spring -clound

4.集成spring-cloud-stream,可解决与消息中间件的耦合问题，
也就是说可以很方便的切换消息中间件【目前kafka和rabbitmq】
    同时项目中包含如下情况和
    4.1 项目默认消息通道【input,output】
    4.2 自定义项目通道【myInput,myOutput】
    4.3 自定义消息中转站【A-->b-->c】[myTranceInput,myTranceOutput]
    4.4 消息分组【即同一组内的消息只会有一个接收端消费，不会被重复消费】
    4.5 分区未完成【TODO】












为什么需要SpringCloud Stream消息驱动呢？
简单来说：解耦和
比方说我们用到了RabbitMQ和Kafka，由于这两个消息中间件的架构上的不同，
像RabbitMQ有exchange，kafka有Topic，partitions分区，这些中间件的差
异性导致我们实际项目开发给我们造成了一定的困扰，我们如果用了两个消息队列的其中一种，

后面的业务需求，我想往另外一种消息队列进行迁移，这时候无疑就是一个灾难性的，
一大堆东西都要重新推倒重新做，因为它跟我们的系统耦合了，这时候springcloud Stream
给我们提供了一种解耦合的方式。
Spring Cloud Stream由一个中间件中立的核组成。应用通过Spring Cloud Stream插入
的input(相当于消费者consumer，它是从队列中接收消息的)和output(相当于生产者producer
，它是从队列中发送消息的。)通道与外界交流。
通道通过指定中间件的Binder实现与外部代理连接。业务开发者不再关注具体消息中间件，
只需关注Binder对应用程序提供的抽象概念来使用消息中间件实现业务即可。

Binder
　通过定义绑定器作为中间层，实现了应用程序与消息中间件(Middleware)细节之间的隔离。
通过向应用程序暴露统一的Channel通过，使得应用程序不需要再考虑各种不同的消息中间件
的实现。当需要升级消息中间件，或者是更换其他消息中间件产品时，我们需要做的就是更换
对应的Binder绑定器而不需要修改任何应用逻辑 。甚至可以任意的改变中间件的类型而不需
要修改一行代码。目前只提供了RabbitMQ和Kafka的Binder实现。

Springcloud Stream还有个好处就是像Kafka一样引入了一点分区的概念，
像RabbitMQ不支持分区的队列，你用了SpringCloud Stream技术，它就会帮RabbitMQ引入
了分区的特性，SpringCloud Stream就是天然支持分区的，我们用起来还是很方便的









