TODO  
本地项目搭建不起来
整不起来，mysql存在失误tx_isolation 不存在，
es这边一直存在问题

#这个感觉应该是有服务间依赖相互调用的情况下适用
各服务间调用情况追踪，找到耗时部分优化等等


应用官方zipkinjar包来作为服务端

以下为项目启动的配置说明：
存储类型：
STORAGE_TYPE = Cassandra, MySQL and Elasticsearch.

环境变量： 
* `SPARK_MASTER`: Spark master to submit the job to; Defaults to `local[*]`
* `ZIPKIN_LOG_LEVEL`: Log level for zipkin-related status; Defaults to INFO


Cassandra Storage：
Cassandra is used when STORAGE_TYPE=cassandra 
or STORAGE_TYPE=cassandra3.
cassandra is compatible with Zipkin's Legacy Cassandra storage component.
cassandra3 is compatible with Zipkin's Cassandra v3 storage component.

* `CASSANDRA_KEYSPACE`: The keyspace to use. Defaults to "zipkin".
* `CASSANDRA_CONTACT_POINTS`: Comma separated list of hosts / ip addresses part of Cassandra cluster. Defaults to localhost
* `CASSANDRA_LOCAL_DC`: The local DC to connect to (other nodes will be ignored)
* `CASSANDRA_USERNAME` and `CASSANDRA_PASSWORD`: Cassandra authentication. Will throw an exception on startup if authentication fails
* `CASSANDRA_USE_SSL`: Requires `javax.net.ssl.trustStore` and `javax.net.ssl.trustStorePassword`, defaults to false.
* `STRICT_TRACE_ID`: When false, dependency linking only looks at 64 bits of a trace ID, defaults to true.


MySQL Storage
* `MYSQL_DB`: The database to use. Defaults to "zipkin".
* `MYSQL_USER` and `MYSQL_PASS`: MySQL authentication, which defaults to empty string.
* `MYSQL_HOST`: Defaults to localhost
* `MYSQL_TCP_PORT`: Defaults to 3306
* `MYSQL_USE_SSL`: Requires `javax.net.ssl.trustStore` and `javax.net.ssl.trustStorePassword`, defaults to false.


Elasticsearch Storage：
* `ES_INDEX`: The index prefix to use when generating daily index names. Defaults to zipkin.
* `ES_DATE_SEPARATOR`: The separator used when generating dates in index.
                       Defaults to '-' so the queried index look like zipkin-yyyy-DD-mm
                       Could for example be changed to '.' to give zipkin-yyyy.MM.dd
* `ES_HOSTS`: A comma separated list of elasticsearch hosts advertising http. Defaults to
              localhost. Add port section if not listening on port 9200. Only one of these hosts
              needs to be available to fetch the remaining nodes in the cluster. It is
              recommended to set this to all the master nodes of the cluster. Use url format for
              SSL. For example, "https://yourhost:8888"
* `ES_NODES_WAN_ONLY`: Set to true to only use the values set in ES_HOSTS, for example if your
                       elasticsearch cluster is in Docker. Defaults to false
* `ES_USERNAME` and `ES_PASSWORD`: Elasticsearch basic authentication. Use when X-Pack security
                                   (formerly Shield) is in place. By default no username or
                                   password is provided to elasticsearch.
                               



