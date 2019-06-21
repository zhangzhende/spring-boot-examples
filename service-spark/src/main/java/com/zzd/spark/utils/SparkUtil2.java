package com.zzd.spark.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.*;

/**
 * @Description sparkstreaming get data from kafka and wordcount
 * @ClassName SparkUtil2
 * @Author zzd
 * @Create 2019/6/20 19:57
 * @Version 1.0
 **/
public class SparkUtil2 {
    public static void main(String[] args) throws InterruptedException {

        SparkConf conf = new SparkConf().setMaster("local").setAppName("SparkStreamingOnKafkaDirected");
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(10));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");//多个可用ip可用","隔开
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "sparkStreaming");
        Collection<String> topics = Arrays.asList("test");//配置topic，可以是数组

        JavaInputDStream<ConsumerRecord<String, String>> javaInputDStream = KafkaUtils.createDirectStream(
                jsc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaParams));
        JavaPairDStream<String, String> javaPairDStream = javaInputDStream.mapToPair(new PairFunction<ConsumerRecord<String, String>, String, String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, String> call(ConsumerRecord<String, String> consumerRecord) throws Exception {
                return new Tuple2<>(consumerRecord.key(), consumerRecord.value());
            }
        });
        JavaDStream<String> words = javaPairDStream.flatMap(new FlatMapFunction<Tuple2<String, String>, String>() { //如果是Scala，由于SAM转换，所以可以写成val words = lines.flatMap { line => line.split(" ")}
            private static final long serialVersionUID = 1L;

            public Iterator<String> call(Tuple2<String, String> tuple) throws Exception {
                return Arrays.asList(tuple._2.split(" ")).iterator();
            }
        });
        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            private static final long serialVersionUID = 1L;

            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });
        JavaPairDStream<String, Integer> wordsCount = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() { //对相同的Key，进行Value的累计（包括Local和Reducer级别同时Reduce）
            private static final long serialVersionUID = 1L;

            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        wordsCount.print();
        jsc.start();
        jsc.awaitTermination();
        jsc.close();

    }
}
