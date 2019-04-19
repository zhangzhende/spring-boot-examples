package com.zzd.spring.elasticsearch.otherController;

import com.zzd.spring.elasticsearch.config.ElasticSearchConfig;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhende on 2018/5/3.
 * 聚合分析
 */
@RestController
public class AggController {
    @Autowired
    ElasticSearchConfig elasticSearchConfig;

    /**
     * 聚合分析获取最大值
     *
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/maxAgg")
    public Object maxAgg() throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("agg").field("price");
        SearchResponse response = client.prepareSearch("books")
                .addAggregation(maxAggregationBuilder).get();
        Max agg = response.getAggregations().get("agg");
        double value = agg.getValue();
        map.put("agg max  value", value);
        return map;
    }

    /**
     * 其他很多指标
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/manyValue")
    public Object manyValue() throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        //Min
        MinAggregationBuilder minAggregationBuilder=AggregationBuilders.min("minagg").field("price");
        SearchResponse minresponse=client.prepareSearch("books").addAggregation(minAggregationBuilder).execute().actionGet();
        Min min =minresponse.getAggregations().get("minagg");
        map.put("min agg value",min.getValue());

        //Sum
        SumAggregationBuilder sumAggregationBuilder=AggregationBuilders.sum("sumagg").field("price");
        SearchResponse sumResponse=client.prepareSearch("books").addAggregation(sumAggregationBuilder).execute().actionGet();
        Sum sum=sumResponse.getAggregations().get("sumagg");
        map.put("max agg value",sum.getValue());

        //Avg
        AvgAggregationBuilder avgAggregationBuilder=AggregationBuilders.avg("avgagg").field("price");
        SearchResponse avgResponse=client.prepareSearch("books").addAggregation(avgAggregationBuilder).execute().actionGet();
        Avg avg=avgResponse.getAggregations().get("avgagg");
        map.put("avg agg value",avg.getValue());

        //stats常规指标，最大max，最小min，平均avg，总和sum，数量count
        StatsAggregationBuilder statsAggregationBuilder=AggregationBuilders.stats("statsagg").field("price");
        SearchResponse statsResponse=client.prepareSearch("books").execute().actionGet();
        Stats stats=statsResponse.getAggregations().get("statsagg");
        Map statsMap=new HashMap();
        statsMap.put("minValue",stats.getMin());
        statsMap.put("maxValue",stats.getMax());
        statsMap.put("avgVale",stats.getAvg());
        statsMap.put("sumValue",stats.getSum());
        statsMap.put("countValue",stats.getCount());
        map.put("stats agg value",statsMap);

        //Extend Stats 高级指标，平方和，方差，标准差，平均值 加减两个标准差的区间
        ExtendedStatsAggregationBuilder extendedStatsAggregationBuilder=AggregationBuilders.extendedStats("extendstatsagg").field("price");
        SearchResponse extendStatsResponse=client.prepareSearch("books").addAggregation(extendedStatsAggregationBuilder).execute().actionGet();
        ExtendedStats extendedStats=extendStatsResponse.getAggregations().get("extentdstatsagg");
        Map<String,Object> extendMap=new HashMap<>();
        extendMap.put("min",extendedStats.getMin());
        extendMap.put("max",extendedStats.getMax());
        extendMap.put("avg",extendedStats.getAvg());
        extendMap.put("sum",extendedStats.getSum());
        extendMap.put("stdDeviation",extendedStats.getStdDeviation());
        extendMap.put("sumOfSquares",extendedStats.getSumOfSquares());
        extendMap.put("variance",extendedStats.getVariance());
        map.put("ExtendStats agg value",extendMap);

        //Cardinality 统计该字段有多少种值，类似于distinct后查看
        CardinalityAggregationBuilder cardinalityAggregationBuilder=AggregationBuilders.cardinality("cardinalityagg").field("language");
        SearchResponse cardinalityResponse=client.prepareSearch("books").addAggregation(cardinalityAggregationBuilder).execute().actionGet();
        Cardinality cardinality=cardinalityResponse.getAggregations().get("cardinalityagg");
        map.put("cardinality agg value",cardinality.getValue());

        //value count 可以按照字段统计文档数量，如统计索引中包含  author字段的文档数量
        ValueCountAggregationBuilder valueCountAggregationBuilder=AggregationBuilders.count("countagg").field("author");
        SearchResponse countResponse=client.prepareSearch("books").addAggregation(valueCountAggregationBuilder).execute().actionGet();
        ValueCount valueCount=countResponse.getAggregations().get("countagg");
        map.put("valuecount agg value",valueCount.getValue());
        return map;
    }

}
