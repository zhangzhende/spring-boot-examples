package com.zzd.spring.elasticsearch.otherController;

import com.zzd.spring.elasticsearch.config.ElasticSearchConfig;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.date.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.geodistance.GeoDistanceAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.ip.IpRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhende on 2018/5/5.
 * 桶聚合
 */
@RestController
public class BucketController {
    @Autowired
    ElasticSearchConfig elasticSearchConfig;

    /**
     * terms aggregation
     * 用于分组聚合
     * 对于分组后的每个桶进行指标聚合
     *
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/termsAgg")
    public Object termsAgg() throws UnknownHostException {
        Map<Object, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("per_count").field("language");
        SearchResponse response = client.prepareSearch("books").addAggregation(termsAggregationBuilder).execute().actionGet();
        Terms genders = response.getAggregations().get("per_count");
        for (Terms.Bucket entry : genders.getBuckets()) {
            map.put(entry.getKey(), entry.getDocCount());
        }
        return map;
    }

    /**
     * 过滤器聚合
     * 过滤器聚合，将符合过滤条件的文档放到一个桶
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/filterAgg")
    public Object filterAgg() throws UnknownHostException {
        Map<Object, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        FilterAggregationBuilder filterAggregationBuilder = AggregationBuilders.filter("agg", QueryBuilders.termQuery("title", "java"));
        SearchResponse response = client.prepareSearch("books").addAggregation(filterAggregationBuilder).execute().actionGet();
        Filter filter = response.getAggregations().get("agg");
        map.put("DocCount", filter.getDocCount());
        return map;
    }

    /**
     *
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/filtersAgg")
    public Object filtersAgg() throws UnknownHostException {
        Map<Object, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        FiltersAggregationBuilder filtersAggregationBuilder = AggregationBuilders
                .filters("agg",new FiltersAggregator.KeyedFilter("java",QueryBuilders.termQuery("title","java")),
                        new FiltersAggregator.KeyedFilter("python",QueryBuilders.termQuery("title","python")));
        SearchResponse response = client.prepareSearch("books").addAggregation(filtersAggregationBuilder).execute().actionGet();
        Filters filters = response.getAggregations().get("agg");
        for (Filters.Bucket entry:     filters.getBuckets()        ) {
            map.put(entry.getKey(),entry.getDocCount());
        }
        return map;
    }

    /**
     * 范围聚合
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/rangeAgg")
    public Object rangeAgg() throws UnknownHostException {
        Map<Object, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        RangeAggregationBuilder rangeAgg = AggregationBuilders
                .range("agg")
                .field("price")
                .addUnboundedTo(50)
                .addRange(50,80)
                .addUnboundedFrom(80);
        SearchResponse response = client.prepareSearch("books").addAggregation(rangeAgg).execute().actionGet();
        Filters filters = response.getAggregations().get("agg");
        for (Filters.Bucket entry:     filters.getBuckets()        ) {
            map.put(entry.getKey(),entry.getDocCount());
        }
        return map;
    }

    /**
     * 日期范围的聚合
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/dateRangeAgg")
    public Object dateRangeAgg() throws UnknownHostException {
        Map<Object, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        DateRangeAggregationBuilder dateRangeAgg = AggregationBuilders
                .dateRange("agg")
                .field("publish_time")
                .format("yyyy-MM-dd")
                .addUnboundedTo("now-24M/M")
                .addUnboundedFrom("now+24M/M");
        SearchResponse response = client.prepareSearch("books").addAggregation(dateRangeAgg).execute().actionGet();
        Range ranges = response.getAggregations().get("agg");
        for (Range.Bucket entry:     ranges.getBuckets()        ) {
            map.put(entry.getKey(),entry.getDocCount());
        }
        return map;
    }

    /**
     * 地理位置
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/geoAgg")
    public Object geoAgg() throws UnknownHostException {
        Map<Object, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        GeoDistanceAggregationBuilder geoDistanceAggregationBuilder = AggregationBuilders
                .geoDistance("agg",new GeoPoint(34.3412700000,108.9398400000))
                .field("location")
                .unit(DistanceUnit.KILOMETERS)
                .addUnboundedTo(500)
                .addRange(500,1000)
                .addUnboundedFrom(1000);
        SearchResponse response = client.prepareSearch("geo").addAggregation(geoDistanceAggregationBuilder).execute().actionGet();
        Range ranges = response.getAggregations().get("agg");
        for (Range.Bucket entry:     ranges.getBuckets()        ) {
            map.put(entry.getKey(),entry.getDocCount());
        }
        return map;
    }

    /**
     * IP地址范围
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/ipRangeAgg")
    public Object ipRangeAgg() throws UnknownHostException {
        Map<Object, Object> map = new HashMap<>();
        TransportClient client = elasticSearchConfig.getInstance();
        IpRangeAggregationBuilder ipAgg = AggregationBuilders
                .ipRange("agg")
                .field("ip")
                .addUnboundedTo("100.0.0.5")
                .addUnboundedFrom("100.0.0.5");
        SearchResponse response = client.prepareSearch("ipinfo").addAggregation(ipAgg).execute().actionGet();
        Range ranges = response.getAggregations().get("agg");
        for (Range.Bucket entry:     ranges.getBuckets()        ) {
            map.put(entry.getKey(),entry.getDocCount());
        }
        return map;
    }

}
