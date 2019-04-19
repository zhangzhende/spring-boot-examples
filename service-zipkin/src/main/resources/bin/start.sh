--es启动方式
java -DSTORAGE_TYPE=elasticsearch -DES_HOSTS=http://192.168.2.60:9200 -DCLUSTER=NLX_ES -DES_INDEX=mytest -jar zipkin-server-2.12.8-exec.jar
--es后台启动
nohup java -DSTORAGE_TYPE=elasticsearch -DES_HOSTS=http://192.168.2.60:9200 -DCLUSTER=NLX_ES -DES_INDEX=mytest -jar zipkin-server-2.12.8-exec.jar &
--mysql 
java -DSTORAGE_TYPE=mysql -DMYSQL_DB=zipkin -DMYSQL_USER=root -DMYSQL_PASS=root -DMYSQL_HOST=localhost -DMYSQL_TCP_PORT=3306 -jar zipkin-server-2.12.8-exec.jar
--mysql 后台启动
nohup java -DSTORAGE_TYPE=mysql -DMYSQL_DB=zipkin -DMYSQL_USER=root -DMYSQL_PASS=root -DMYSQL_HOST=localhost -DMYSQL_TCP_PORT=3306 -jar zipkin-server-2.12.8-exec.jar &
配置说明：


export STORAGE_TYPE=elasticsearch

export ES_HOSTS=http://127.0.0.1:9200

export ES_MAX_REQUESTS=100

export ES_INDEX=zipkin

export ES_DATE_SEPARATOR=-

export ES_INDEX_REPLICAS=3

export ES_INDEX_SHARDS=5

export KAFKA_TOPIC=zipkin

export KAFKA_STREAMS=100

export KAFKA_MAX_MESSAGE_SIZE=10485760

export KAFKA_BOOTSTRAP_SERVERS=127.0.0.1:9092