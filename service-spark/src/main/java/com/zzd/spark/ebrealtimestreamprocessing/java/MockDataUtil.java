package com.zzd.spark.ebrealtimestreamprocessing.java;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * @Description 自动生成模拟数据
 * @ClassName MockDataUtil
 * @Author zzd
 * @Date 2019/10/8 16:28
 * @Version 1.0
 **/
public class MockDataUtil {
    private static final Log logger = LogFactory.getLog(MockDataUtil.class);
    public static void main(String[] args){
        final Random random=new Random();
        final String[] provinces=new String[]{"anhui","jiangsu","zhejiang","fujian"};
        final Map<String,String[]> cities=new HashMap<>();
        cities.put("anhui",new String[]{"hefei","anqing","chizhou"});
        cities.put("jiangsu",new String[]{"nanjing","suzhou","xuzhou"});
        cities.put("zhejiang",new String[]{"hangzhou","wenzhou","ningbo"});
        cities.put("fujian",new String[]{"fuzhou","xiamen","sanming"});
        final String[] ips=new String[]{
                "192.168.30.1",
                "192.168.30.2",
                "192.168.30.3",
                "192.168.30.4",
                "192.168.30.5",
                "192.168.30.6",
                "192.168.30.7",
                "192.168.30.8",
                "192.168.30.9",
                "192.168.30.10"
        };
        Properties kafkaConf=new Properties();
        kafkaConf.setProperty("bootstrap.servers", "localhost:9092");
        kafkaConf.setProperty("serializer.class","kafka.serialiazer.StringEncoder");
        kafkaConf.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaConf.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaConf.setProperty("metadata.broker.list","localhost:9092");
        ProducerConfig producerConfig=new ProducerConfig(kafkaConf);
        final Producer<String,String> producer=new KafkaProducer<String, String>(kafkaConf);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                格式：timestamp,ip,user_id,ad_id,province,city
                while(true){
                    Long timestamp=new Date().getTime();
                    String ip=ips[random.nextInt(10)];
                    int userId=random.nextInt(10000);
                    int adId=random.nextInt(100);
                    String province =provinces[random.nextInt(4)];
                    String city=cities.get(province)[random.nextInt(3)];
                    String clickedAd=timestamp+"\t"+ip+"\t"+userId+"\t"+adId+"\t"+province+"\t"+city;
                    logger.info(clickedAd);
                    producer.send(new ProducerRecord<String,String>("test",clickedAd));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
