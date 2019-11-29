package com.zzd.spark.ebrealtimestreamprocessing.java;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 说明类的用途
 * @ClassName AdClickedStreamingStats
 * @Author zzd
 * @Date 2019/10/9 11:08
 * @Version 1.0
 **/
public class AdClickedStreamingStats {
    /**
     * d首先配置SparkConfig
     */
//    SparkConf conf=new SparkConf().setMaster("localhost:7077").setAppName("AdClickedStreamingStats");
    SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("AdClickedStreamingStats");
    /**
     * 创建SparkSteamContext
     */
    JavaStreamingContext jsc = new JavaStreamingContext(conf, Duration.apply(10 * 1000));
    Map<String ,String> kafkaParams=new HashMap<>();
    kafkaParams



}
